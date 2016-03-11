package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class TotalResourcesPercept extends Percept {

    public TotalResourcesPercept(int cminerals, int minerals, int cgas, int gas, int csupply, int tsupply) {
        super(Percepts.TotalResources, new Numeral(cminerals), new Numeral(minerals), new Numeral(cgas), new Numeral(gas), new Numeral(csupply), new Numeral(tsupply));
    }
}
