package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.WorkerActivityPercept;
import jnibwapi.types.UnitType.UnitTypes;
import java.util.*;
import jnibwapi.*;

public class WorkerActivityPerceiver extends Perceiver {
    private final BWApiUtility util;
    
    public WorkerActivityPerceiver(JNIBWAPI api, BWApiUtility util) {
        super(api);
        this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> percepts = new ArrayList<>();
        
        for (Unit unit : this.api.getMyUnits()) {
            if(unit.getType().isWorker()){
                if(unit.isGatheringGas()){
                    percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringGas"));
                } else if (unit.isGatheringMinerals()){
                    percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringMinerals"));
                } else if (unit.isConstructing()){
                    percepts.add(new WorkerActivityPercept(unit.getID(), "constructing"));
                }
            }
        }
        
        return percepts;
    }
}
