package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class WorkerActivityPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public WorkerActivityPercept(int id, String activity) {
    super(Percepts.WorkerActivity, new Numeral(id), new Identifier(activity));
  }
}
