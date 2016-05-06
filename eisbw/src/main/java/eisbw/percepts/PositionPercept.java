package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class PositionPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public PositionPercept(int xpos, int ypos) {
    super(Percepts.Position, new Numeral(xpos), new Numeral(ypos));
  }
}
