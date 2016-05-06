package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class BuildTilePositionPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public BuildTilePositionPercept(int xpos, int ypos) {
    super(Percepts.BuildTilePosition, new Numeral(xpos), new Numeral(ypos));
  }
}
