package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;
import java.util.*;
import jnibwapi.*;

public class TransporterPerceiver extends UnitPerceiver {
	
	private BWApiUtility util;
	
    public TransporterPerceiver(JNIBWAPI api, BWApiUtility util, Unit unit) {
        super(api, unit);
		this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
		
		List<Unit> loadedUnits = unit.getLoadedUnits();
		
		percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().getSpaceProvided()));
		for (Unit u : loadedUnits) {
			percepts.add(new UnitLoadedPercept(u.getID(), unit.getType().getName()));
		}
		
        return percepts;
    }
}
