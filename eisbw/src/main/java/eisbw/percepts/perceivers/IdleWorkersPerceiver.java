package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.IdleWorkerPercept;

public class IdleWorkersPerceiver extends Perceiver {
    private final BWApiUtility util;
    public IdleWorkersPerceiver(JNIBWAPI api, BWApiUtility util) {
        super(api);
        this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        List<Unit> units = api.getMyUnits();
        for (Unit unit : units) {
            if (unit.isIdle() && unit.getType().isWorker()) {
                percepts.add(new IdleWorkerPercept(util.getUnitName(unit)));
            }
        }
        
        return percepts;
    }
}
