package eisbw.units.terran;

import eisbw.percepts.perceivers.IPerceiver;
import eisbw.units.StarcraftUnit;
import java.util.ArrayList;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class SCV extends StarcraftUnit {

    public SCV(JNIBWAPI api, Unit unit, ArrayList<IPerceiver> perceptGenerators) {
        super(api, unit, perceptGenerators);
    }
}
