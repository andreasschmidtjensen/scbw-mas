package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import java.util.List;
import jnibwapi.*;

public class ProducerUnitPerceiver extends UnitPerceiver {

    public ProducerUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
