package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.StimPercept;

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
