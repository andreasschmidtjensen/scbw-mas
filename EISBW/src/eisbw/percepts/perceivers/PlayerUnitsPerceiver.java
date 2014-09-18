package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.FriendlyPercept;
import java.util.*;
import jnibwapi.*;

public class PlayerUnitsPerceiver extends Perceiver {
    private final BWApiUtility util;
    
    public PlayerUnitsPerceiver(JNIBWAPI api, BWApiUtility util) {
        super(api);
        this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> percepts = new ArrayList<>();
        
        for (Unit unit : this.api.getMyUnits()) {
            percepts.add(new FriendlyPercept(util.getUnitName(unit), api.getUnitType(unit.getTypeID()).getName(), unit.getID(), unit.getPosition().getWX(), unit.getPosition().getWY()));
        }
        
        return percepts;
    }
}
