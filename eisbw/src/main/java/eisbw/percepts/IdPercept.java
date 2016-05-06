package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class IdPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public IdPercept(int id) {
    super(Percepts.Id, new Numeral(id));
  }
}