package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BwapiUtility;
import eisbw.percepts.CarryingPercept;
import eisbw.percepts.GatheringPercept;
import eisbw.percepts.VespeneGeyserPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.ArrayList;
import java.util.List;

public class GathererUnitPerceiver extends UnitPerceiver {

  public GathererUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> result = new ArrayList<>();
    if (unit.isGatheringGas() || unit.isGatheringMinerals()) {
      Percept percept = new GatheringPercept(unit.isGatheringGas());
      result.add(percept);
    }
    if (unit.isCarryingGas() || unit.isCarryingMinerals()) {
      result.add(new CarryingPercept());
    }

    for (Unit u : api.getNeutralUnits()) {
      if (u.getType() == UnitType.UnitTypes.Resource_Vespene_Geyser) {
        Percept percept = new VespeneGeyserPercept(u.getID(), u.getResources(), 
            u.getResourceGroup(), u.getPosition().getBX(),
            u.getPosition().getBY());
        result.add(percept);
      }
    }

    for (Unit u : api.getMyUnits()) {
      if (u == unit) {
        continue;
      }
      if (u.isGatheringGas() || unit.isGatheringMinerals()) {
        Percept percept = new GatheringPercept(BwapiUtility.getUnitName(u), u.isGatheringGas());
        result.add(percept);
      }
    }

    return result;
  }
}
