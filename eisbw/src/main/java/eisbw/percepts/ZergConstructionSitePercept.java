package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ZergConstructionSitePercept extends Percept {

    public ZergConstructionSitePercept(int x, int y) {
        super(Percepts.ZergConstructionSite, new Numeral(x), new Numeral(y));
    }
}
