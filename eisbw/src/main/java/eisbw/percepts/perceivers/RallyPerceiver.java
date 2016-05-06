package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.RallyPointPercept;
import eisbw.percepts.RallyUnitPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position.Positions;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class RallyPerceiver extends UnitPerceiver {
  public RallyPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    if (!unit.getRallyPosition().equals(Positions.None)) {
      percepts.add(new RallyPointPercept(unit.getRallyPosition().getBX(), 
          unit.getRallyPosition().getBY()));
    }

    if (unit.getRallyUnit() != null) {
      percepts.add(new RallyUnitPercept(unit.getRallyUnit().getID()));
    }

    return percepts;
  }
}
