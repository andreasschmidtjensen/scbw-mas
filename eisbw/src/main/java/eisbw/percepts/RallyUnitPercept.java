package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class RallyUnitPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public RallyUnitPercept(int id) {
    super(Percepts.RallyUnit, new Numeral(id));
  }

}
