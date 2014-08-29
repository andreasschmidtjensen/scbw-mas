package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class TotalMineralsPercept extends Percept {

    public TotalMineralsPercept(int x) {
        super(Percepts.TotalMinerals, new Numeral(x));
    }
}
