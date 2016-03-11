package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

public class Research extends StarcraftAction {

    public Research(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        if (parameters.size() == 1) { // type
            boolean isTechType = parameters.get(0) instanceof Identifier && utility.getTechType(((Identifier) parameters.get(0)).getValue()) != null;
            return isTechType;
        }

        return false;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        TechType techType = utility.getTechType(((Identifier) parameters.get(0)).getValue());

        return !techType.isTargetsPosition() && !techType.isTargetsUnits();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();
        TechType techType = utility.getTechType(((Identifier) parameters.get(0)).getValue());
        if(!unit.research(techType)) {
        	throw new ActException("Couldn't research "+techType.getName());
        }
    }

    @Override
    public String toString() {
        return "research(techId)";
    }
}
