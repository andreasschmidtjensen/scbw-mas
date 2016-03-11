package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class BuildTilePositionPercept extends Percept {

    public BuildTilePositionPercept(int x, int y) {
        super(Percepts.BuildTilePosition, new Numeral(x), new Numeral(y));
    }
}
