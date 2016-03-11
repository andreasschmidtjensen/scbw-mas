package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Parameter;

public class Stop  extends StarcraftAction {

    public Stop(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return parameters.size() == 0;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        UnitType unitType = unit.getType();
        return !unitType.isBuilding();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        unit.stop(false);
    }

    @Override
    public String toString() {
        return "stop"; 
    } 
}
