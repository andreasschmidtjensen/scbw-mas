package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class UnitTypePercept extends Percept {

    public UnitTypePercept(String type) {
        super(Percepts.UnitType, new Identifier(type));
    }
}
