package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class EnergyPercept extends Percept {

    public EnergyPercept(int current, int max) {
        super(Percepts.Energy, new Numeral(current), new Numeral(max));
    }

}
