package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.LinkedList;

public class Train extends StarcraftAction {

  public Train(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) {
      return parameters.get(0) instanceof Identifier
          && utility.getUnitType(((Identifier) parameters.get(0)).getValue()) != null;
    }
    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().isProduceCapable();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    UnitType unitType = utility.getUnitType(((Identifier) parameters.get(0)).getValue());
    unit.train(unitType);
  }

  @Override
  public String toString() {
    return "train(Type)";
  }
}
