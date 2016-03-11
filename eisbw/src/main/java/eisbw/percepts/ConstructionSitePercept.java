package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ConstructionSitePercept extends Percept {

    public ConstructionSitePercept(int x, int y) {
        super(Percepts.ConstructionSite, new Numeral(x), new Numeral(y));
    }
}
