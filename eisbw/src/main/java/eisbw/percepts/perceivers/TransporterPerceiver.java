package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class TransporterPerceiver extends UnitPerceiver {
  public TransporterPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    List<Unit> loadedUnits = unit.getLoadedUnits();

    percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().getSpaceProvided()));
    for (Unit u : loadedUnits) {
      percepts.add(new UnitLoadedPercept(u.getID(), unit.getType().getName()));
    }

    return percepts;
  }
}
