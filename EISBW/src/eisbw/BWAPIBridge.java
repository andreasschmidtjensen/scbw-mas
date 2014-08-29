package eisbw;

import eis.*;
import eis.exceptions.*;
import eis.iilang.*;
import eisbw.actions.*;
import eisbw.percepts.GameStartPercept;
import eisbw.percepts.perceivers.*;
import eisbw.units.*;
import java.util.*;
import java.util.Map;
import java.util.logging.*;
import jnibwapi.*;
import jnibwapi.types.UnitType;

public class BWAPIBridge extends EIDefaultImpl {

    // TODO: Check 64bit version of client bridge
    private static final Logger logger = Logger.getLogger(BWAPIBridge.class.getName());
    public static final int TRAINING_QUEUE_MAX = 5;
    private final Thread apiThread;
    private static JNIBWAPI bwapi;
    private BWApiUtility bwApiUtility;
    private final StarcraftUnitFactory unitFactory;
    private final Map<String, Unit> units;
    private final Map<Integer, String> unitNames;
    private List<Percept> mapPercepts = null;
    private final ActionProvider actionProvider;
    private boolean gameStarted = false;

    private Map<Unit, Action> pendingActions = new HashMap<>();

    public static JNIBWAPI getGame() {
        return bwapi;
    }

    public static void main(String[] args) throws ManagementException {
        BWAPIBridge env = new BWAPIBridge();
        env.init(Collections.EMPTY_MAP);
    }

    public BWAPIBridge() {
        super();
        this.units = new HashMap<>();
        this.unitNames = new HashMap<>();
        bwapi = new JNIBWAPI(bwApiListener, true);
        this.bwApiUtility = new BWApiUtility(bwapi);
        this.unitFactory = new StarcraftUnitFactory(bwapi, this.bwApiUtility);
        this.actionProvider = new ActionProvider();
        actionProvider.loadActions(bwapi);

        apiThread = new Thread() {
            @Override
            public void run() {
                bwapi.start();
            }
        };
    }

    @Override
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        super.init(parameters);
        try {
            addEntity("player");
        } catch (EntityException ex) {
            Logger.getLogger(BWAPIBridge.class.getName()).log(Level.SEVERE, null, ex);
        }
        apiThread.start();
		
		setState(EnvironmentState.PAUSED);
    }

    @Override
    protected LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws PerceiveException, NoEnvironmentException {
        LinkedList<Percept> percepts = new LinkedList<>();
        if (!gameStarted) {
            return percepts;
        }

        if (mapPercepts != null) {
            percepts.addAll(mapPercepts);
        }

        Unit unit = units.get(entity);
        if (unit != null) {
            StarcraftUnit scu = this.unitFactory.Create(unit);
            percepts.addAll(scu.perceive());
        }
		
		percepts.addAll(new TotalResourcesPerceiver(bwapi).perceive());

        Map<UnitType, Integer> count = new HashMap<>();
        for (Unit myUnit : bwapi.getMyUnits()) {
            UnitType unitType = myUnit.getType();
            if (!count.containsKey(unitType)) {
                count.put(unitType, 0);
            }
            count.put(unitType, count.get(unitType) + 1);
        }
        for (UnitType unitType : count.keySet()) {
            percepts.add(new Percept("unit", new Identifier(unitType.getName()), new Numeral(count.get(unitType))));
        }

        for (Unit u : bwapi.getNeutralUnits()) {
            UnitType unitType = u.getType();
            if (u.isVisible()) {
                if (UnitTypesEx.isMineralField(unitType)) {
                    Percept p = new Percept("mineralField");
                    p.addParameter(new Numeral(u.getID()));
                    p.addParameter(new Numeral(u.getResources()));
                    p.addParameter(new Numeral(u.getResourceGroup()));
                    percepts.add(p);
                } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
                    Percept p = new Percept("vespeneGeyser");
                    p.addParameter(new Numeral(u.getID()));
                    p.addParameter(new Numeral(u.getResources()));
                    p.addParameter(new Numeral(u.getResourceGroup()));
                    percepts.add(p);
                } else if (UnitTypesEx.isRefinery(unitType)) {
                    Percept p = new Percept("refinery");
                    p.addParameter(new Numeral(u.getID()));
                    p.addParameter(new Numeral(u.getResources()));
                    p.addParameter(new Numeral(u.getResourceGroup()));
                    percepts.add(p);
                }
            }
        }

        percepts.add(new GameStartPercept());

        return percepts;
    }

    @Override
    protected boolean isSupportedByEnvironment(Action action) {
        return actionProvider.getAction(action.getName()) != null;
    }

    @Override
    protected boolean isSupportedByType(Action action, String string) {
        return true;
    }

    @Override
    protected boolean isSupportedByEntity(Action act, String name) {
        Unit unit = units.get(name);
        String actionName = act.getName();

        StarcraftAction action = actionProvider.getAction(actionName);

        // if action is invalid, we need to provide a failure message (which can only be provided by performEntityAction, so return true in that case)
        return !action.isValid(act) || action.canExecute(unit, act);
    }

    @Override
    protected synchronized Percept performEntityAction(String name, Action act) throws ActException {
        Unit unit = units.get(name);

        // cant act during construction
        if (unit.isBeingConstructed()) {
            return null;
        }

        if (!pendingActions.containsKey(unit)) {
            String actionName = act.getName();

            StarcraftAction action = actionProvider.getAction(actionName);
            // Action might be invalid
            if (action.isValid(act)) {
                logger.info("[" + name + "] Pending action: " + act.toProlog());
                pendingActions.put(unit, act);
            } else {
                throw new ActException(ActException.FAILURE, "Action must be of the form " + action.toString() + " (was " + act.toProlog() + ").");
            }

        }
        return null;

    }

    public void register(Unit u) throws RuntimeException {
        String unitName = bwApiUtility.getUnitName(u);
        units.put(unitName, u);
        unitNames.put(u.getID(), unitName);
        try {
			System.out.println("u.getType().getName(): " + bwApiUtility.getEISUnitType(u));
            addEntity(unitName, bwApiUtility.getEISUnitType(u));
        } catch (EntityException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String requiredVersion() {
        return "0.3";
    }
    private final BWAPIEventListener bwApiListener = new BWAPIEventListener() {
        @Override
        public void connected() {

        }

        @Override
        public void matchStart() {
            logger.info("Game started...");

            // set game speed to 30 (0 is the fastest. Tournament speed is 20)
            // You can also change the game speed from within the game by "/speed X" command.
            bwapi.setGameSpeed(5);
            bwapi.enableUserInput();

            bwapi.drawIDs(true);
            bwapi.drawHealth(true);
            bwapi.drawTargets(true);

            mapPercepts = new ArrayList<>();
            gameStarted = true;
            //jnibwapi.Map map = bwapi.getMap();
        }

        @Override
        public void matchFrame() {
            synchronized (BWAPIBridge.this) {
                Iterator<Unit> it = pendingActions.keySet().iterator();
                while (it.hasNext()) {
                    Unit unit = it.next();
                    Action act = pendingActions.get(unit);

                    String actionName = act.getName();

                    StarcraftAction action = actionProvider.getAction(actionName);
                    logger.info("[" + bwApiUtility.getUnitName(unit) + "] Performing action: " + act.toProlog());
                    try {
                        action.execute(unit, act);
                    } catch (ActException ex) {
                        logger.log(Level.WARNING, "Could not execute " + act.toProlog(), ex);
                    }

                    it.remove();
                }
            }
//            for (int x = 0; x < bwapi.getMap().getSize().getBX(); x++) {
//                for (int y = 0; y < bwapi.getMap().getSize().getBY(); y++) {
//                    bwapi.drawBox(new Position(x, y, Position.PosType.BUILD), new Position(x + 1, y + 1, Position.PosType.BUILD), BWColor.Yellow, false, false);
//                }
//            }
            
        }

        @Override
        public void keyPressed(int i) {
        }

        @Override
        public void playerLeft(int i) {
        }

        @Override
        public void nukeDetect() {
        }

        @Override
        public void unitDiscover(int i) {
        }

        @Override
        public void unitEvade(int i) {
        }

        @Override
        public void unitShow(int i) {
        }

        @Override
        public void unitHide(int i) {
        }

        @Override
        public void unitCreate(int i) {
            Unit u = bwapi.getUnit(i);
            if (bwapi.getMyUnits().contains(u)) {
                register(u);
            }
        }

        @Override
        public void unitDestroy(int i) {
            if (unitNames.containsKey(i)) {
                String unitName = unitNames.get(i);
                units.remove(unitName);
                try {
                    deleteEntity(unitName);
                } catch (EntityException | RelationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        @Override
        public void unitMorph(int i) {
        }

        @Override
        public void sendText(String text) {
        }

        @Override
        public void receiveText(String text) {
        }

        @Override
        public void unitRenegade(int unitID) {
        }

        @Override
        public void saveGame(String gameName) {
        }

        @Override
        public void unitComplete(int unitID) {
        }

        @Override
        public void playerDropped(int playerID) {
        }

        @Override
        public void matchEnd(boolean winner) {
        }

        @Override
        public void nukeDetect(Position pstn) {
        }
    };
}
