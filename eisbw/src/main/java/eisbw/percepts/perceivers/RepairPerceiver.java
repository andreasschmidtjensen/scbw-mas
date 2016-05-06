package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.RepairPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class RepairPerceiver extends Perceiver {

  public RepairPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new ArrayList<>();

    for (Unit unit : api.getMyUnits()) {
      if (unit.getType().isMechanical()) {
        int hp = unit.getHitPoints();
        int maxHp = unit.getType().getMaxHitPoints();
        if (hp < maxHp) {
          percepts.add(new RepairPercept(unit));
        }
      }
    }

    return percepts;
  }

}
