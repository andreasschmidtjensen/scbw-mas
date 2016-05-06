package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.GasPercept;
import eisbw.percepts.MineralsPercept;
import eisbw.percepts.SupplyPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;

import java.util.ArrayList;
import java.util.List;

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
