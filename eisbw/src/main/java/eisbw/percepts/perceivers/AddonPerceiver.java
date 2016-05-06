package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.AddonPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class AddonPerceiver extends UnitPerceiver {
  public AddonPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    if (unit.getAddon() != null) {
      percepts.add(new AddonPercept(unit.getAddon().getType().getName()));
    }

    return percepts;
  }
}
