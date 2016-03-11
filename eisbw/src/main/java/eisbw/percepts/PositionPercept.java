package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class PositionPercept extends Percept {

    public PositionPercept(int x, int y) {
        super(Percepts.Position, new Numeral(x), new Numeral(y));
    }
}
