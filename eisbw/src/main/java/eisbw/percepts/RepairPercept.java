package eisbw.percepts;

import jnibwapi.Unit;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RepairPercept extends Percept {

    public RepairPercept(Unit unit) {
        super(Percepts.RequiresRepair, new Numeral(unit.getID()), new Identifier(unit.getType().getName()), new Numeral(unit.getHitPoints()), new Numeral(unit.getType().getMaxHitPoints()));
    }
}
