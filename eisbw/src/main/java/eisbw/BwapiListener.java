package eisbw;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.units.StarcraftUnitFactory;
import jnibwapi.BWAPIEventListener;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BwapiListener implements BWAPIEventListener {

  private JNIBWAPI bwapi;
  private Game game;
  private ActionProvider actionProvider;
  private Map<Unit, Action> pendingActions;
  private StarcraftUnitFactory factory;
  private UpdateThread updateThread;

  public BwapiListener(Game game) {
    bwapi = new JNIBWAPI(this, true);
    this.game = game;
    actionProvider = new ActionProvider();
    actionProvider.loadActions(bwapi);
    pendingActions = new HashMap<Unit, Action>();
    factory = new StarcraftUnitFactory(bwapi);

    new Thread() {
      @Override
      public void run() {
        bwapi.start();
      }
    }.start();
    
    updateThread = new UpdateThread(game, bwapi);
  }

  @Override
  public void connected() {

  }

  @Override
  public void matchStart() {
    // set game speed to 30 (0 is the fastest. Tournament speed is 20)
    // You can also change the game speed from within the game by
    // "/speed X" command.
    bwapi.setGameSpeed(5);
    bwapi.enableUserInput();

    // START THE DEBUGGER, this has to be fixed with mas2g arguments.
    // if (configuration.getDebugMode().equals("true")) {
    // debug = new DebugWindow();
    // }
    bwapi.drawIDs(true);
    bwapi.drawHealth(true);
    bwapi.drawTargets(true);
    updateThread.start();
  }

  @Override
  public void matchFrame() {
    synchronized (pendingActions) {
      Iterator<Unit> it = pendingActions.keySet().iterator();
      while (it.hasNext()) {
        Unit unit = it.next();
        Action act = pendingActions.get(unit);

        StarcraftAction action = getAction(act);
        action.execute(unit, act);

        it.remove();
      }
      // if (configuration.getDebugMode().equals("true")) {
      // debug.debug(bwapi);
      // }
     // game.update(bwapi);
    }
  }

  @Override
  public void matchEnd(boolean winner) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyPressed(int keyCode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sendText(String text) {
    // TODO Auto-generated method stub

  }

  @Override
  public void receiveText(String text) {
    // TODO Auto-generated method stub

  }

  @Override
  public void playerLeft(int playerID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void nukeDetect(Position p) {
    // TODO Auto-generated method stub

  }

  @Override
  public void nukeDetect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitDiscover(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitEvade(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitShow(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitHide(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitCreate(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitDestroy(int id) {
    if (game.getUnits().getUnitNames().containsKey(id)) {
      String unitName = game.getUnits().getUnitNames().get(id);
      game.getUnits().deleteUnit(unitName, id);
    }
  }

  @Override
  public void unitMorph(int id) {
    if (game.getUnits().getUnitNames().containsKey(id)) {
      String unitName = game.getUnits().getUnitNames().get(id);
      Unit unit = game.getUnits().getUnits().get(unitName);
      if (bwapi.getMyUnits().contains(unit)) {
        game.getUnits().deleteUnit(unitName, id);;
        game.getUnits().addUnit(unit,factory);
      }
    }
  }

  @Override
  public void unitRenegade(int unitID) {
    // TODO Auto-generated method stub

  }

  @Override
  public void saveGame(String gameName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void unitComplete(int unitId) {
    Unit unit = bwapi.getUnit(unitId);
    if (bwapi.getMyUnits().contains(unit)) {
      game.getUnits().addUnit(unit, factory);
    }
  }

  @Override
  public void playerDropped(int playerID) {
    // TODO Auto-generated method stub

  }

  protected boolean isSupportedByEntity(Action act, String name) {
    Unit unit = game.getUnits().getUnits().get(name);

    StarcraftAction action = getAction(act);
    return action.isValid(act) && action.canExecute(unit, act);
  }

  public StarcraftAction getAction(Action action) {
    return actionProvider.getAction(action.getName() + "/" + action.getParameters().size());
  }

  public void performEntityAction(String name, Action act) throws ActException {
    Unit unit = game.getUnits().getUnits().get(name);

    // cant act during construction
    if (!unit.isBeingConstructed()) {
      if (!pendingActions.containsKey(unit)) {
        StarcraftAction action = getAction(act);
        // Action might be invalid
        if (action.isValid(act)) {
          pendingActions.put(unit, act);
        }

      }
    }

  }

}
