package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RallyPointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public RallyPointPercept(int xpos, int ypos) {
    super(Percepts.RallyPoint, new Numeral(xpos), new Numeral(ypos));
  }

}
