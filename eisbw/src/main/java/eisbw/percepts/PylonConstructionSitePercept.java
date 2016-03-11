package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class PylonConstructionSitePercept extends Percept {

    public PylonConstructionSitePercept(int x, int y) {
        super(Percepts.PylonConstructionSite, new Numeral(x), new Numeral(y));
    }
}
