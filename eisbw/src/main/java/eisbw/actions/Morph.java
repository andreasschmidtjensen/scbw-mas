package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import java.util.LinkedList;

public class Morph extends StarcraftAction {

  public Morph(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) {
      UnitType ut = utility.getUnitType(((Identifier) parameters.get(0)).getValue());
      boolean check = parameters.get(0) instanceof Identifier && ut != null;
      return check;
    }
    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    boolean check = api.getSelf().getRace().getID() == RaceTypes.Zerg.getID();
    return check;
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> params = action.getParameters();
    String type = ((Identifier) params.get(0)).getValue();
    unit.morph(utility.getUnitType(type));
  }

  @Override
  public String toString() {
    return "morph(Type)";
  }
}
