package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.IsLoadedPercept;

public class IsLoadedUnitPerceiver extends UnitPerceiver {

    public IsLoadedUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        if (unit.isLoaded()) {
            result.add(new IsLoadedPercept());
        }
        return result;
    }
}
