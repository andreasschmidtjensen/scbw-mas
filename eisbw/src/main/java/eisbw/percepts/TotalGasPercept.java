package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class TotalGasPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public TotalGasPercept(int gas) {
    super(Percepts.TotalGas, new Numeral(gas));
  }
}
