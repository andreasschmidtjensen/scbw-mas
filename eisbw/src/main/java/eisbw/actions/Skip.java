package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Parameter;

public class Skip extends StarcraftAction {

    public Skip(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return parameters.size() == 0;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return true;
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
    	
    }

    @Override
    public String toString() {
        return "skip";
    }
}
