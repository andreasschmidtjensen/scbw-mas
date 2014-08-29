package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class SupplyPercept extends Percept {

    public SupplyPercept(int current, int max) {
        super(Percepts.Supply, new Numeral(current), new Numeral(max));
    }
}
