package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.*;
import java.util.LinkedList;
import jnibwapi.*;
import jnibwapi.types.UnitType;

public class Build extends StarcraftAction {

    public Build(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        if (parameters.size() == 3) {
            return parameters.get(0) instanceof Identifier && utility.getUnitType(((Identifier) parameters.get(0)).getValue()) != null
                    && parameters.get(1) instanceof Numeral
                    && parameters.get(2) instanceof Numeral;
        }
        return false;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return unit.getType().isWorker();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> params = action.getParameters();
        String type = ((Identifier) params.get(0)).getValue();
        int tx = ((Numeral) params.get(1)).getValue().intValue();
        int ty = ((Numeral) params.get(2)).getValue().intValue();
        //UnitType unitType = unit.getType();

		// TODO: Check that it is a building
        boolean result = api.build(unit.getID(), tx, ty, utility.getUnitType(type).getID());
        if (!result) {
            throw new ActException(ActException.FAILURE);
        }
    }

    @Override
    public String toString() {
        return "build(Type, X, Y)";
    }
}
