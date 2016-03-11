package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class UseOnPosition extends StarcraftAction {

    public UseOnPosition(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();

        if (parameters.size() == 3) { // type, x, y
            boolean isTechType = parameters.get(0) instanceof Identifier && utility.getTechType(((Identifier) parameters.get(0)).getValue()) != null;
            return isTechType
                    && parameters.get(1) instanceof Numeral
                    && parameters.get(2) instanceof Numeral;
        }

        return false;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        TechType techType = utility.getTechType(((Identifier) parameters.get(0)).getValue());
        return techType.isTargetsPosition();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();

        TechType techType = utility.getTechType(((Identifier) parameters.get(0)).getValue());
        
        int x = ((Numeral) parameters.get(1)).getValue().intValue();
        int y = ((Numeral) parameters.get(2)).getValue().intValue();
        unit.useTech(techType, new Position(x, y));
    }

    @Override
    public String toString() {
        return "useOnPosition(techType, x, y)";
    }
}
