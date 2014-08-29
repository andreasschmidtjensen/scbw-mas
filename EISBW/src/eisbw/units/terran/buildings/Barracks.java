package eisbw.units.terran.buildings;

import eisbw.percepts.perceivers.IPerceiver;
import eisbw.units.StarcraftUnit;
import java.util.ArrayList;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class Barracks extends StarcraftUnit {

    public Barracks(JNIBWAPI api, Unit unit, ArrayList<IPerceiver> perceptGenerators) {
        super(api, unit, perceptGenerators);
    }
}
