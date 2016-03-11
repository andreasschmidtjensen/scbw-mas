package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RallyUnitPercept extends Percept {

    public RallyUnitPercept(int id) {
        super(Percepts.RallyUnit, new Numeral(id));
    }

}
