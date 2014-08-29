package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.StimPercept;
import java.util.*;
import jnibwapi.*;

public class StimUnitPerceiver extends UnitPerceiver {

    public StimUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        if (unit.isStimmed()) {
            result.add(new StimPercept());
        }
        return result;
    }
}
