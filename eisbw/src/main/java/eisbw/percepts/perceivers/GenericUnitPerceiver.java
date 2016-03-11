package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.BuildTilePositionPercept;
import eisbw.percepts.EnergyPercept;
import eisbw.percepts.IdPercept;
import eisbw.percepts.IdlePercept;
import eisbw.percepts.IsBeingConstructedPercept;
import eisbw.percepts.IsStuck;
import eisbw.percepts.PositionPercept;
import eisbw.percepts.UnitTypePercept;

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
        if( !unit.isCompleted()){
            result.add(new IsBeingConstructedPercept());
        }
        result.add(new PositionPercept(unit.getPosition().getBX(), unit.getPosition().getBY()));
        
        if(unit.isStuck()){
            result.add(new IsStuck());
        }
        
        result.add(new BuildTilePositionPercept(unit.getPosition().getBX(), unit.getPosition().getBY()));
        result.add(new EnergyPercept(unit.getEnergy(), unit.getType().getMaxEnergy()));
        
        return result;
    }
}
