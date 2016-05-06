package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class LoadablePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public LoadablePercept(String typeName, int id, int current, int max) {
    super(Percepts.Loadable, new Identifier(typeName), 
        new Numeral(id), new Numeral(current), new Numeral(max));
  }
}
