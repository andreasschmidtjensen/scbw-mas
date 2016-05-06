package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class GasPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public GasPercept(int quantity) {
    super(Percepts.Gas, new Numeral(quantity));
  }
}
