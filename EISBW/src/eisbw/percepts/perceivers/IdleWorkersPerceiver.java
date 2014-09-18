package eisbw.percepts.perceivers;

import eis.iilang.*;
import eisbw.BWApiUtility;
import eisbw.percepts.IdleWorkerPercept;
import java.util.*;
import jnibwapi.*;
import jnibwapi.types.UnitType.UnitTypes;

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
