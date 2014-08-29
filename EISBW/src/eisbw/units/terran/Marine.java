package eisbw.units.terran;

import eisbw.percepts.perceivers.IPerceiver;
import eisbw.units.StarcraftUnit;
import java.util.List;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class Marine extends StarcraftUnit {

    public Marine(JNIBWAPI api, Unit unit, List<IPerceiver> perceptGenerators) {
        super(api, unit, perceptGenerators);
    }
}
