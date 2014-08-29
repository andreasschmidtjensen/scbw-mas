package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class TotalGasPercept extends Percept {

    public TotalGasPercept(int x) {
        super(Percepts.TotalGas, new Numeral(x));
    }
}
