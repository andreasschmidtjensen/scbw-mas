package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MineralFieldPercept extends Percept {

    public MineralFieldPercept(int id, int resources, int resourceGroup) {
        super(Percepts.MineralField, new Numeral(id),new Numeral(resources), new Numeral(resourceGroup));
    }
}
