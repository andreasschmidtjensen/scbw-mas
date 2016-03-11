package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RallyPointPercept extends Percept {

    public RallyPointPercept(int x, int y) {
        super(Percepts.RallyPoint, new Numeral(x), new Numeral(y));
    }

}
