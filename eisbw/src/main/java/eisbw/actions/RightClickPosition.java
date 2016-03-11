package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class RightClickPosition extends StarcraftAction {

    public RightClickPosition(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return parameters.size() == 2 && parameters.get(0) instanceof Numeral && parameters.get(1) instanceof Numeral;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return true;
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();
        int x = ((Numeral) parameters.get(0)).getValue().intValue();
        int y = ((Numeral) parameters.get(1)).getValue().intValue();
        
		Position pos = new Position(x, y, Position.PosType.BUILD);
		unit.rightClick(pos, false);
    }

    @Override
    public String toString() {
        return "rightClick(x,y)";
    }
}
