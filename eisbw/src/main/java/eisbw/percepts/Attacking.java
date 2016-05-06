package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class Attacking extends Percept {

  private static final long serialVersionUID = 1L;

  public Attacking(int unit, int targetUnit) {
    super(Percepts.Attacking, new Numeral(unit), new Numeral(targetUnit));
  }
}