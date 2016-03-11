package eisbw.actions;

import java.util.LinkedList;

import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class AttackMove  extends StarcraftAction {

    public AttackMove(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        return parameters.size() == 2 && parameters.get(0) instanceof Numeral && parameters.get(1) instanceof Numeral;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        UnitType unitType = unit.getType();
        return unitType.isCanMove() && unitType.isAttackCapable();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();
        int x = ((Numeral) parameters.get(0)).getValue().intValue();
        int y = ((Numeral) parameters.get(1)).getValue().intValue();
        unit.attack(new Position(x, y, Position.PosType.BUILD), false);
    }

    @Override
    public String toString() {
        return "attack(x, y)"; //To change body of generated methods, choose Tools | Templates.
    } 
}
