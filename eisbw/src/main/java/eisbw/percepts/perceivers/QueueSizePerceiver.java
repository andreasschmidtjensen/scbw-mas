package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.QueueSizePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

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
