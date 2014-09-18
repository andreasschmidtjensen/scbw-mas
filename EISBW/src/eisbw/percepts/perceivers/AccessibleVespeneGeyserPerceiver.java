package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.UnitTypesEx;
import eisbw.percepts.*;
import java.awt.Point;
import java.util.*;
import jnibwapi.*;
import jnibwapi.types.UnitType;

public class AccessibleVespeneGeyserPerceiver extends UnitPerceiver {
    
    public AccessibleVespeneGeyserPerceiver(JNIBWAPI api, Unit unit) {
        super(api,unit);
    }

   

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        for (Unit u : api.getNeutralUnits()) {
            UnitType unitType = u.getType();
            if (u.isVisible()) {if (UnitTypesEx.isVespeneGeyser(unitType)) {
                    AccessibleVespeneGeyserPercept p = new AccessibleVespeneGeyserPercept(
                            u.getPosition().getBX(),
                            u.getPosition().getBY());
                    percepts.add(p);
                }
            }
        }
		// TODO: Locations near minerals and geysers are not buildable
        return percepts;
    }
}
