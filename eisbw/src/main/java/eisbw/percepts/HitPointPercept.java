package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class HitPointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public HitPointPercept(int hitPoint) {
    super(Percepts.HitPoint, new Numeral(hitPoint));
  }
}