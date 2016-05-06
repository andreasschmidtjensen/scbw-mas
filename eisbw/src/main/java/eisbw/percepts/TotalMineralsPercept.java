package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class TotalMineralsPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public TotalMineralsPercept(int minerals) {
    super(Percepts.TotalMinerals, new Numeral(minerals));
  }
}
