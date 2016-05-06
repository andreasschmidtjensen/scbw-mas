package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.StimPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class StimUnitPerceiver extends UnitPerceiver {

  public StimUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> result = new ArrayList<>();
    if (unit.isStimmed()) {
      result.add(new StimPercept());
    }
    return result;
  }
}
