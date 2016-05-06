package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class FriendlyPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * FriendlyPercept.
   */
  public FriendlyPercept(String agentName, String typeName, int id, int health, int shields, int bx,
      int by) {
    super(Percepts.Friendly, new Identifier(agentName), new Identifier(typeName), new Numeral(id),
        new Numeral(health), new Numeral(shields), new Numeral(bx), new Numeral(by));
  }
}
