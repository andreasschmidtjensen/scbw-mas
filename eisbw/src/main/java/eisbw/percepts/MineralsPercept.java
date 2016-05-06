package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MineralsPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public MineralsPercept(int quantity) {
    super(Percepts.Minerals, new Numeral(quantity));
  }
}
