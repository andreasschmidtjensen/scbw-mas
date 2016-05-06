package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class BuildUnitPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public BuildUnitPercept(int buildUnitId) {
    super(Percepts.BuildUnit, new Numeral(buildUnitId));
  }
}
