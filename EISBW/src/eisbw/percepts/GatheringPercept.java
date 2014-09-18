package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class GatheringPercept extends Percept {

    public GatheringPercept(boolean isGatheringGas) {
        super(Percepts.Gathering, new Identifier(isGatheringGas ? Identifiers.Vespene : Identifiers.Mineral));
    }
	
    public GatheringPercept(String unitName, boolean isGatheringGas) {
        super(Percepts.Gathering, new Identifier(unitName), new Identifier(isGatheringGas ? Identifiers.Vespene : Identifiers.Mineral));
    }
}
