package eisbw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jnibwapi.BWAPIEventListener;
import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import jnibwapi.util.BWColor;
import eis.EIDefaultImpl;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.percepts.GameStartPercept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.UnitPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.perceivers.TotalResourcesPerceiver;
import eisbw.units.StarcraftUnit;
import eisbw.units.StarcraftUnitFactory;


public class BWAPIBridge extends EIDefaultImpl {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(BWAPIBridge.class
      .getName());
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
  public void init(Map<String, Parameter> parameters)
      throws ManagementException {
    super.init(parameters);
    apiThread.start();

    setState(EnvironmentState.PAUSED);
    try {
      addEntity("player");
    } catch (EntityException ex) {
      Logger.getLogger(BWAPIBridge.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected LinkedList<Percept> getAllPerceptsFromEntity(String entity)
      throws PerceiveException, NoEnvironmentException {

    LinkedList<Percept> percepts = new LinkedList<>();
    if (!gameStarted) {
      return percepts;
    }

    if (mapPercepts != null) {
      percepts.addAll(mapPercepts);
    }
    Unit unit = units.get(entity);
    if (unit != null) {
      StarcraftUnit scu = this.unitFactory.create(unit);
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
      percepts.add(new UnitPercept(unitType.getName(), count.get(unitType)));
    }

    for (Unit u : bwapi.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept p = new MineralFieldPercept(u.getID(),
              u.getResources(), u.getResourceGroup(), u.getPosition().getBX(),
              u.getPosition().getBY());
          percepts.add(p);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept p = new VespeneGeyserPercept(u.getID(),
              u.getResources(), u.getResourceGroup(), u.getPosition().getBX(),
              u.getPosition().getBY());
          percepts.add(p);

        }
      }
    }

    percepts.add(new GameStartPercept());

    return percepts;
  }

  @Override
  protected boolean isSupportedByEnvironment(Action action) {
    return getAction(action) != null;
  }

  @Override
  protected boolean isSupportedByType(Action action, String string) {
    return true;
  }

  private StarcraftAction getAction(Action action) {
    return actionProvider.getAction(action.getName() + "/"
        + action.getParameters().size());
  }

  @Override
  protected boolean isSupportedByEntity(Action act, String name) {
    Unit unit = units.get(name);

    StarcraftAction action = getAction(act);

    // if action is invalid, we need to provide a failure message (which can only be provided by performEntityAction, so return true in that case)
    return !action.isValid(act) || action.canExecute(unit, act);
  }

  @Override
  protected synchronized Percept performEntityAction(String name, Action act)
      throws ActException {
    Unit unit = units.get(name);

    // cant act during construction
    if (unit.isBeingConstructed()) {
      return null;
    }

    if (!pendingActions.containsKey(unit)) {
      StarcraftAction action = getAction(act);
      // Action might be invalid
      if (action.isValid(act)) {
        pendingActions.put(unit, act);
      } else {
        throw new ActException(ActException.FAILURE,
            "Action must be of the form " + action.toString() + " (was "
                + act.toProlog() + ").");
      }

    }
    return null;

  }

  public void register(Unit u) throws RuntimeException {
    String unitName = bwApiUtility.getUnitName(u);
    units.put(unitName, u);
    unitNames.put(u.getID(), unitName);
    try {
      addEntity(unitName, bwApiUtility.getEISUnitType(u));
    } catch (EntityException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public String requiredVersion() {
    return "0.5";
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

          StarcraftAction action = getAction(act);
          try {
            action.execute(unit, act);
          } catch (ActException ex) {
            logger
                .log(Level.WARNING, "Could not execute " + act.toProlog(), ex);
          }
          it.remove();
        }
      }
      for (ChokePoint cp : bwapi.getMap().getChokePoints()) {
        bwapi.drawLine(cp.getFirstSide(), cp.getSecondSide(), BWColor.Yellow,
            false);
        bwapi.drawCircle(cp.getCenter(), (int) cp.getRadius(), BWColor.Red,
            false, false);
        bwapi.drawText(cp.getCenter(), "(" + cp.getCenter().getBX() + ","
            + cp.getCenter().getBY() + ")", false);
      }

      for (BaseLocation loc : bwapi.getMap().getBaseLocations()) {
        bwapi.drawCircle(loc.getCenter(), 75, BWColor.Purple, false, false);
        bwapi.drawText(loc.getPosition(), "(" + loc.getCenter().getBX() + ","
            + loc.getCenter().getBY() + ")", false);
      }
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
      if (unitNames.containsKey(i)) {
        String unitName = unitNames.get(i);
        Unit u = units.get(unitName);
        if (bwapi.getMyUnits().contains(u)) {
          register(u);
        }
      }
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
      Unit u = bwapi.getUnit(unitID);
      if (bwapi.getMyUnits().contains(u)) {
        register(u);
      }
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
