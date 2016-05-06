package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class QueueSizePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public QueueSizePercept(int queueSize) {
    super(Percepts.QueueSize, new Numeral(queueSize));
  }
}
