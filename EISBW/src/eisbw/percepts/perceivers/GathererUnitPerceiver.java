package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.GatheringPercept;
import java.util.*;
import jnibwapi.*;

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
        return result;
    }
}
