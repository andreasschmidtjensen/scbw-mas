package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class UnitLoadedPercept extends Percept {

    public UnitLoadedPercept(int id, String type) {
        super(Percepts.UnitLoaded, new Numeral(id), new Identifier(type));
    }
}
