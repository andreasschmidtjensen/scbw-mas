package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;

public class Lift extends StarcraftAction {

  public Lift(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 0;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().isBuilding() && unit.getType().getRaceID() == 1;
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.lift();
  }

  @Override
  public String toString() {
    return "lift()";
  }
}
