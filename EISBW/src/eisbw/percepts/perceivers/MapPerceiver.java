package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;
import java.util.*;
import jnibwapi.JNIBWAPI;
import jnibwapi.*;

public class MapPerceiver extends Perceiver {

    public MapPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        jnibwapi.Map map = api.getMap();

        Percept mapPercept = new MapPercept(map.getWidth(), map.getHeight());
        percepts.add(mapPercept);

        for (BaseLocation location : map.getBaseLocations()) {
            Percept basePercept = new BasePercept(location.getTx(), location.getTy(), location.isStartLocation(), location.getRegionID());
            percepts.add(basePercept);
        }

        return percepts;
    }
}
