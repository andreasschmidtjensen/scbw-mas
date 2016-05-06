package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MineralFieldPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public MineralFieldPercept(int id, int resources, int resourceGroup, int xpos, int ypos) {
    super(Percepts.MineralField, new Numeral(id), new Numeral(resources), 
        new Numeral(resourceGroup), new Numeral(xpos), new Numeral(ypos));
  }
}
