package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.SiegePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class SiegeUnitPerceiver extends UnitPerceiver {

  public SiegeUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> result = new ArrayList<>();
    if (unit.isSieged()) {
      result.add(new SiegePercept());
    }
    return result;
  }
}
