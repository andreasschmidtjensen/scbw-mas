package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class EnemyPercept extends Percept {

    public EnemyPercept(String name, int id, int wX, int wY, int bX, int bY) {
        super(Percepts.Enemy, new Identifier(name), new Numeral(id), new Numeral(wX), new Numeral(wY), new Numeral(bX), new Numeral(bY));
    }

}
