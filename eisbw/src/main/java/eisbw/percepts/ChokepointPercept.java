package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ChokepointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ChokepointPercept(int xpos, int ypos) {
    super(Percepts.Chokepoint, new Numeral(xpos), new Numeral(ypos));
  }
}
