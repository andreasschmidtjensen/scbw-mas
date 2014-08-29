package eisbw.percepts;

import eis.iilang.*;

public class Attacking extends Percept {
    public Attacking(int unit, int targetUnit) {
        super(Percepts.Attacking, new Numeral(unit), new Numeral(targetUnit));
    }
}