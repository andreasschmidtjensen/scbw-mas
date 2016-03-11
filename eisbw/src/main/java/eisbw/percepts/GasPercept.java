package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class GasPercept extends Percept {

    public GasPercept(int quantity) {
        super(Percepts.Gas, new Numeral(quantity));
    }
}
