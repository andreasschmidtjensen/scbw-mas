package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.CarryingPercept;
import eisbw.percepts.GatheringPercept;
import eisbw.percepts.VespeneGeyserPercept;

public class GathererUnitPerceiver extends UnitPerceiver {

    public GathererUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        if (unit.isGatheringGas() || unit.isGatheringMinerals()) {
            Percept p = new GatheringPercept(unit.isGatheringGas());
            result.add(p);
        }
	if (unit.isCarryingGas() || unit.isCarryingMinerals()){
            result.add(new CarryingPercept());
        }
		
		for (Unit u : api.getNeutralUnits()) {
			if (u.getType() == UnitType.UnitTypes.Resource_Vespene_Geyser) {
				Percept p = new VespeneGeyserPercept(u.getID(), 
                                        u.getResources(), 
                                        u.getResourceGroup(), 
                                        u.getPosition().getBX(),
                                        u.getPosition().getBY());
				result.add(p);
			}
		}
		
		BWApiUtility util = new BWApiUtility(api);
		for (Unit u : api.getMyUnits()) {
			if (u == unit) {
				continue;
			}
			if (u.isGatheringGas() || unit.isGatheringMinerals()) {
				Percept p = new GatheringPercept(util.getUnitName(u), u.isGatheringGas());
				result.add(p);
			}
		}
		
        return result;
    }
}
