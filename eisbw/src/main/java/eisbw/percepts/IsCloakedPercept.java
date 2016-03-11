package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class IsCloakedPercept extends Percept {
    public IsCloakedPercept(String typeName, int id) {
        super(Percepts.IsCloaked , new Identifier(typeName), new Numeral(id));
    }
}
