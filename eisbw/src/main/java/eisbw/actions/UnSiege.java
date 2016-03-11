package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Parameter;

public class UnSiege extends StarcraftAction {

    public UnSiege(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return parameters.size() == 0;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return unit.isSieged();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
    	boolean res = unit.unsiege();
        if(!res){
        	throw new ActException("Couldn't unsiege "+unit.getType().getName());
        }
    }

    @Override
    public String toString() {
        return "unsiege()";
    }
}
