package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MineralsPercept extends Percept {

    public MineralsPercept(int quantity) {
        super(Percepts.Minerals, new Numeral(quantity));
    }
}
