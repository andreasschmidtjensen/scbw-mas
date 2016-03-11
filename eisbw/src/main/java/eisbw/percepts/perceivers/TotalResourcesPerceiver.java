package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import eis.iilang.Percept;
import eisbw.percepts.TotalResourcesPercept;

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
