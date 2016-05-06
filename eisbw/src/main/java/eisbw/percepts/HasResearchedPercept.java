package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class HasResearchedPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public HasResearchedPercept(String name) {
    super(Percepts.HasResearched, new Identifier(name));
  }
}
