package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class IdleWorkerPercept extends Percept {

    public IdleWorkerPercept(String name) {
        super(Percepts.IdleWorker, new Identifier(name));
    }
}
