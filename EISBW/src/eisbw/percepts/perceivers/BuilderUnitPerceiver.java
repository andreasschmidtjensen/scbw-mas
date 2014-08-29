package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.ConstructingPercept;
import java.util.*;
import jnibwapi.*;

public class BuilderUnitPerceiver extends UnitPerceiver {

    public BuilderUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();

        if (unit.isConstructing()) {
            percepts.add(new ConstructingPercept());
        }
        
        return percepts;
    }
}
