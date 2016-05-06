package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class BasePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public BasePercept(int xpos, int ypos, boolean isStart, int regionId) {
    super(Percepts.Base, new Numeral(xpos), new Numeral(ypos), 
        new TruthValue(isStart), new Numeral(regionId));
  }
}
