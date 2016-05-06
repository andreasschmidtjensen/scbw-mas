package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.LinkedList;

public class Gather extends StarcraftAction {

  public Gather(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 1 && parameters.get(0) instanceof Numeral;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    UnitType unitType = unit.getType();
    return unitType.isWorker();
  }

  @Override
  public void execute(Unit unit, Action action) {
    int targetId = ((Numeral) action.getParameters().get(0)).getValue().intValue();
    unit.gather(api.getUnit(targetId), false);
  }

  @Override
  public String toString() {
    return "gather(targetID)";
  }
}
