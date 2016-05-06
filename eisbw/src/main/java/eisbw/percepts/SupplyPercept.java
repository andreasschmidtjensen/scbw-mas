package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class SupplyPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public SupplyPercept(int current, int max) {
    super(Percepts.Supply, new Numeral(current), new Numeral(max));
  }
}
