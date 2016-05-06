package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;

public class Siege extends StarcraftAction {

  public Siege(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 0;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().getName().equals("Terran Siege Tank Tank Mode");
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.siege();
  }

  @Override
  public String toString() {
    return "siege()";
  }
}
