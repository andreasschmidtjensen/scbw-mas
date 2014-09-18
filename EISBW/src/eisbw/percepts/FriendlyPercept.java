package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class FriendlyPercept extends Percept {
    public FriendlyPercept(String agentName, String typeName, int id, int wX, int wY, int bX, int bY) {
        super(Percepts.Friendly, new Identifier(agentName), new Identifier(typeName), new Numeral(id), new Numeral(wX), new Numeral(wY), new Numeral(bX), new Numeral(bY));
    }
}
