package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;
import java.util.*;
import jnibwapi.*;

public class GenericUnitPerceiver extends UnitPerceiver {

    public GenericUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }
    
    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        if (unit.isIdle()) {
            result.add(new IdlePercept());
        }
        
        result.add(new IdPercept(unit.getID()));
        result.add(new UnitTypePercept(unit.getType().getName()));
        result.add(new PositionPercept(unit.getTileX(), unit.getTileY()));
        if( !unit.isCompleted()){
            result.add(new IsBeingConstructedPercept());
        }
        
        return result;
    }
}
