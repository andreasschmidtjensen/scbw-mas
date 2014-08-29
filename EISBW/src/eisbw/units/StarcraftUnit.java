package eisbw.units;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;
import java.util.ArrayList;
import java.util.List;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class StarcraftUnit {

    protected final Unit unit;
    protected final JNIBWAPI api;
    protected final List<IPerceiver> perceivers;

    public StarcraftUnit(JNIBWAPI api, Unit unit, List<IPerceiver> perceivers) {
        this.api = api;
        this.unit = unit;
        this.perceivers = perceivers;
    }

    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        for (IPerceiver perceiver : this.perceivers) {
            percepts.addAll(perceiver.perceive());
        }
        return percepts;
    }
}
