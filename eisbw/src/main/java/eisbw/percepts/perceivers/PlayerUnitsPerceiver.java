package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.FriendlyPercept;
import eisbw.percepts.IsCloakedPercept;
import eisbw.percepts.IsMorphing;

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
              percepts.add(new FriendlyPercept(util.getUnitName(unit), unit.getType().getName(), unit.getID(), unit.getHitPoints(), unit.getShields(), unit.getPosition().getWX(), unit.getPosition().getWY(), unit.getPosition().getBX(), unit.getPosition().getBY()));
            if(unit.isCloaked()){
              percepts.add(new IsCloakedPercept(unit.getType().getName(), unit.getID()));
            }
            if(unit.isMorphing())
              percepts.add(new IsMorphing(unit.getBuildType().getName(), unit.getID()));
        }
        
        return percepts;
    }
}
