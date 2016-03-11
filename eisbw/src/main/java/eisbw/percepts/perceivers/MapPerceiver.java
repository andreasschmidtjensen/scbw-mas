package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import eis.iilang.Percept;
import eisbw.percepts.BasePercept;
import eisbw.percepts.ChokepointPercept;
import eisbw.percepts.MapPercept;

public class MapPerceiver extends Perceiver {

    public MapPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        jnibwapi.Map map = api.getMap();

        Percept mapPercept = new MapPercept(map.getSize().getBX(), map.getSize().getBY());
        percepts.add(mapPercept);

        for (BaseLocation location : map.getBaseLocations()) {
            Percept basePercept = new BasePercept(location.getPosition().getBX(), location.getPosition().getBY(), location.isStartLocation(), location.getRegion().getID());
            percepts.add(basePercept);
        }
		
		for (ChokePoint cp : map.getChokePoints()) {
			Percept chokePercept = new ChokepointPercept(cp.getCenter().getBX(), cp.getCenter().getBY());
			percepts.add(chokePercept);
		}

        return percepts;
    }
}
