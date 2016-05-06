package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class VespeneGeyserPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public VespeneGeyserPercept(int id, int resources, int resourceGroup, int xpos, int ypos) {
    super(Percepts.VespeneGeyser, new Numeral(id), new Numeral(resources), 
        new Numeral(resourceGroup), new Numeral(xpos - 2), new Numeral(ypos - 1));
  }
}
