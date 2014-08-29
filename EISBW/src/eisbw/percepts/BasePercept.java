package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class BasePercept extends Percept {

    public BasePercept(int x, int y, boolean isStart, int regionId) {
        super(Percepts.Base, new Numeral(x), new Numeral(y), new TruthValue(isStart), new Numeral(regionId));
    }
}
