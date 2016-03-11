package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ChokepointPercept extends Percept {

    public ChokepointPercept(int x, int y) {
        super(Percepts.Chokepoint, new Numeral(x), new Numeral(y));
    }
}
