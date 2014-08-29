package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.*;
import java.util.LinkedList;
import jnibwapi.*;
import jnibwapi.types.UnitType;

public class Gather extends StarcraftAction {

    public Gather(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return (parameters.size() == 1 && parameters.get(0) instanceof Numeral);
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        UnitType unitType = unit.getType();
        return unitType.isWorker();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        int targetId = ((Numeral) action.getParameters().get(0)).getValue().intValue();
        boolean result = api.gather(unit.getID(), targetId);
        if (!result) {
            throw new ActException(ActException.FAILURE);
        }
    }

    @Override
    public String toString() {
        return "gather(targetID)";
    }
}
