package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Parameter;

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
    public void execute(Unit unit, Action action) throws ActException {
    	boolean res = unit.lift();
        if(!res){
        	throw new ActException("Couldn't lift "+unit.getType().getName());
        }
    }

    @Override
    public String toString() {
        return "lift()";
    }
}
