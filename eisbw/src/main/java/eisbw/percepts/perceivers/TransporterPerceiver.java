package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;

public class TransporterPerceiver extends UnitPerceiver {
	
	//private BWApiUtility util;
	
    public TransporterPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
		//this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
		
        /*BREAKS HERE SOMETIMES*/
		List<Unit> loadedUnits = unit.getLoadedUnits();
		
		percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().getSpaceProvided()));
		for (Unit u : loadedUnits) {
			percepts.add(new UnitLoadedPercept(u.getID(), unit.getType().getName()));
		}
		
        return percepts;
    }
}
