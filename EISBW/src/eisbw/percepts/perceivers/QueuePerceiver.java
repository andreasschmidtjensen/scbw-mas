package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.QueuePercept;
import java.util.*;
import jnibwapi.*;

public class QueuePerceiver extends UnitPerceiver {

    public QueuePerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        result.add(new QueuePercept(unit.getTrainingQueueSize()));
        return result;
    }
}
