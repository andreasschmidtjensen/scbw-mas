package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class QueuePercept extends Percept {

    public QueuePercept(int queueSize) {
        super(Percepts.Queue, new Numeral(queueSize));
    }
}
