package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class ResearchPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ResearchPercept(String tech) {
    super(Percepts.Research, new Identifier(tech));
  }

}
