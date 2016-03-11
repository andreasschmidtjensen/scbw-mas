package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.AddonPercept;

public class AddonPerceiver extends UnitPerceiver {
    public AddonPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        if(unit.getAddon()!=null){
        	percepts.add(new AddonPercept(unit.getAddon().getType().getName()));
        }
        
        return percepts;
    }
}
