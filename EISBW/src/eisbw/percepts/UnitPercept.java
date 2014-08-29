package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class UnitPercept extends Percept {

    public UnitPercept(String name, int count) {
        super(Percepts.Unit, new Identifier(name), new Numeral(count));
    }
}
