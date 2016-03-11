package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ProtossConstructionSitePercept extends Percept {

    public ProtossConstructionSitePercept(int x, int y) {
        super(Percepts.ProtossConstructionSite, new Numeral(x), new Numeral(y));
    }
}
