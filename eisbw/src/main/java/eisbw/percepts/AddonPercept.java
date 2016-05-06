package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class AddonPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public AddonPercept(String addon) {
    super(Percepts.Addon, new Identifier(addon));
  }

}
