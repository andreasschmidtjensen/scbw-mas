package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RefineryPercept extends Percept {

    public RefineryPercept(int id, int resources, int resourceGroup) {
        super(Percepts.Refinery, new Numeral(id),new Numeral(resources), new Numeral(resourceGroup));
    }
}
