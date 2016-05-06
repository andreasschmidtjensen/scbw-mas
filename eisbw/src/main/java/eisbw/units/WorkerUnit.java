package eisbw.units;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class WorkerUnit extends StarcraftUnit {

  public WorkerUnit(JNIBWAPI api, Unit unit, List<IPerceiver> perceivers) {
    super(api, unit, perceivers);
  }
  
  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();
    for (IPerceiver perceiver : this.perceivers) {
      percepts.addAll(perceiver.perceive());
    }
    return percepts;
  }

}
