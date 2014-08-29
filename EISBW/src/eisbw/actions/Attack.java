package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import java.util.LinkedList;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

public class Attack  extends StarcraftAction {

    public Attack(JNIBWAPI api) {
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
        return unitType.isAttackCapable();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();
        int targetId = ((Numeral) parameters.get(0)).getValue().intValue();
        Unit target = api.getUnit(targetId);
        unit.attack(target, false);
    }

    @Override
    public String toString() {
        return "attack(unitId, targetId)"; 
    } 
}
