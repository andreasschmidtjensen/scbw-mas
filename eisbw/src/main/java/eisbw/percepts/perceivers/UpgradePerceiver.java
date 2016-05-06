package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.UpgradePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class UpgradePerceiver extends UnitPerceiver {
  public UpgradePerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    if (unit.isUpgrading()) {
      percepts.add(new UpgradePercept(unit.getUpgrade().getName()));
    }

    return percepts;
  }
}
