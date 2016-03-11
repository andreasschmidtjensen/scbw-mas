package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.WorkerActivityPercept;

public class WorkerActivityPerceiver extends Perceiver {
    //private final BWApiUtility util;
    
    public WorkerActivityPerceiver(JNIBWAPI api) {
        super(api);
        //this.util = util;
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
                } else {
                	percepts.add(new WorkerActivityPercept(unit.getID(), "idling"));
                }
            }
        }
        
        return percepts;
    }
}
