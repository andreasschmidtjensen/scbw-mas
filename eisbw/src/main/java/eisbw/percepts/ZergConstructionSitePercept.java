package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ZergConstructionSitePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ZergConstructionSitePercept(int xpos, int ypos) {
    super(Percepts.ZergConstructionSite, new Numeral(xpos), new Numeral(ypos));
  }
}
