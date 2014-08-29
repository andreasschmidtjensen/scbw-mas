package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;
import java.util.*;
import jnibwapi.*;

public class AttackingUnitsPerceiver extends Perceiver {
    public AttackingUnitsPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        List<Unit> myUnits = api.getMyUnits();
        
        for(Unit u : myUnits) {
            Unit t = u.getOrderTarget();
            if (t != null) {
                percepts.add(new Attacking(u.getID(), t.getID()));
            }
        }
        
        return percepts;
    }
}
