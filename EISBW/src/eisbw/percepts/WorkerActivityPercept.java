package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.Identifier;

public class WorkerActivityPercept extends Percept {

    public WorkerActivityPercept(int id, String activity) {
        super(Percepts.WorkerActivity, new Numeral(id), new Identifier(activity));
    }
}
