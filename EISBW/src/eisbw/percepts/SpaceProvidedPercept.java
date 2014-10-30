package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class SpaceProvidedPercept extends Percept {

    public SpaceProvidedPercept(int used, int max) {
        super(Percepts.SpaceProvided, new Numeral(used), new Numeral(max));
    }
}
