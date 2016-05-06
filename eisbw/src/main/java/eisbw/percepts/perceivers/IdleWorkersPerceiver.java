package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BwapiUtility;
import eisbw.percepts.IdleWorkerPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class IdleWorkersPerceiver extends Perceiver {

  public IdleWorkersPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    List<Unit> units = api.getMyUnits();
    for (Unit unit : units) {
      if (unit.isIdle() && unit.getType().isWorker()) {
        percepts.add(new IdleWorkerPercept(BwapiUtility.getUnitName(unit)));
      }
    }

    return percepts;
  }
}
