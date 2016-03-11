package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class QueueSizePercept extends Percept {

    public QueueSizePercept(int queueSize) {
        super(Percepts.QueueSize, new Numeral(queueSize));
    }
}
