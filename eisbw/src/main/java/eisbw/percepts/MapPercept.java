package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MapPercept extends Percept {

    public MapPercept(int width, int height) {
        super(Percepts.Map, new Numeral(width), new Numeral(height));
    }
}
