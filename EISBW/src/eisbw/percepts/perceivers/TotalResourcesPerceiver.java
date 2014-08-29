package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;
import java.util.*;
import jnibwapi.*;

public class TotalResourcesPerceiver extends Perceiver {
    public TotalResourcesPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        Player self = api.getSelf();
        
        percepts.add(new TotalResourcesPercept(self.getMinerals() ,self.getCumulativeMinerals(), self.getGas(), self.getCumulativeGas(), self.getSupplyUsed(), self.getSupplyTotal()));
      
        return percepts;
    }
}
