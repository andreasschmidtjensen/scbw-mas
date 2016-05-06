package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class EnemyPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * Enemy percept.
   */
  public EnemyPercept(String name, int id, int health, int shields, boolean isFlying, int bx,
      int by) {
    super(Percepts.Enemy, new Identifier(name), new Numeral(id), new Numeral(health),
        new Numeral(shields), new TruthValue(isFlying), new Numeral(bx), new Numeral(by));
  }

}
