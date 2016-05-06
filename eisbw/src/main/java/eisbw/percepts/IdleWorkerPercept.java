package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class IdleWorkerPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public IdleWorkerPercept(String name) {
    super(Percepts.IdleWorker, new Identifier(name));
  }
}
