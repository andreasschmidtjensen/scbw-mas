package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class EnemyPercept extends Percept {

    public EnemyPercept(String name, int id, int x, int y) {
        super(Percepts.Enemy, new Identifier(name), new Numeral(id), new Numeral(x), new Numeral(y));
    }

}
