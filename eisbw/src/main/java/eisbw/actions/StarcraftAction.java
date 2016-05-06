package eisbw.actions;

import eis.iilang.Action;
import eisbw.BwapiUtility;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public abstract class StarcraftAction {

  protected JNIBWAPI api;
  protected BwapiUtility utility;

  public StarcraftAction(JNIBWAPI api) {
    this.api = api;
  }

  public abstract boolean isValid(Action action);

  public abstract boolean canExecute(Unit unit, Action action);

  public abstract void execute(Unit unit, Action action);

  @Override
  public abstract String toString();
}
