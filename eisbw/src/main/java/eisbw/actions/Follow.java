package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class Follow extends StarcraftAction {

    public Follow(JNIBWAPI api) {
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
        return unitType.isCanMove();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
      LinkedList<Parameter> parameters = action.getParameters();
      int targetId = ((Numeral) parameters.get(0)).getValue().intValue();
      Unit target = api.getUnit(targetId);
      
      if(!unit.follow(target, false)) {
    	  throw new ActException(unit.getID() +" couldn't follow: "+targetId);
      }
    }

    @Override
    public String toString() {
      return "follow(target)";
    }
}