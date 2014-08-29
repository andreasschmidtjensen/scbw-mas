package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;
import java.util.*;
import jnibwapi.*;

public class AvailableResourcesPerceiver extends Perceiver {
    public AvailableResourcesPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        Player self = api.getSelf();
        
        percepts.add(new MineralsPercept(self.getMinerals()));
        percepts.add(new GasPercept(self.getGas()));
        percepts.add(new SupplyPercept(self.getSupplyUsed(), self.getSupplyTotal()));
        
        return percepts;
    }
}
