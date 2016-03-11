package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.QueueSizePercept;

public class QueueSizePerceiver extends UnitPerceiver {

    public QueueSizePerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        result.add(new QueueSizePercept(unit.getTrainingQueueSize()));
        return result;
    }
}
