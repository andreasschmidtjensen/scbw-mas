package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.Attacking;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.ArrayList;
import java.util.List;

public class AttackingUnitsPerceiver extends Perceiver {
  public AttackingUnitsPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();
    List<Unit> myUnits = api.getMyUnits();

    for (Unit unit : myUnits) {
      Unit targetUnit = unit.getOrderTarget();
      if (targetUnit != null) {
        UnitType type = targetUnit.getType();
        if (type.isAttackCapable() || type.isBuilding()) {
          percepts.add(new Attacking(unit.getID(), targetUnit.getID()));
        }
      }
    }

    return percepts;
  }
}
