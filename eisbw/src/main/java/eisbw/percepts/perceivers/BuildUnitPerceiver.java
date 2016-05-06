package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.BuildUnitPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class BuildUnitPerceiver extends UnitPerceiver {

  public BuildUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> result = new ArrayList<>();

    if (unit.isBeingConstructed()) {
      result.add(new BuildUnitPercept(unit.getBuildUnit().getID()));
    }
    return result;
  }
}
