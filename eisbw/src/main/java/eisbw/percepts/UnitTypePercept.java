package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class UnitTypePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public UnitTypePercept(String type) {
    super(Percepts.UnitType, new Identifier(type));
  }
}
