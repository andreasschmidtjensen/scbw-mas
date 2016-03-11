package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

public class BuildAddon extends StarcraftAction {

    public BuildAddon(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        if (parameters.size() == 1) {
        	UnitType ut = utility.getUnitType(((Identifier) parameters.get(0)).getValue());
            return parameters.get(0) instanceof Identifier && ut!=null && ut.isAddon();
        }
        return false;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return unit.getType().isBuilding() && unit.getAddon() == null;
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> params = action.getParameters();
        String type = ((Identifier) params.get(0)).getValue();

        boolean result = unit.buildAddon(utility.getUnitType(type));
        if (!result) {
            throw new ActException(ActException.FAILURE);
        }
    }

    @Override
    public String toString() {
        return "build(Type, X, Y)";
    }
}
