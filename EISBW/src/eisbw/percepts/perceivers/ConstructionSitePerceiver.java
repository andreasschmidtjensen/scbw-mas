package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.UnitTypesEx;
import eisbw.percepts.BasePercept;
import eisbw.percepts.FriendlyPercept;
import eisbw.percepts.MapPercept;
import eisbw.percepts.ConstructionSitePercept;
import java.awt.Point;
import java.util.*;
import jnibwapi.*;
import jnibwapi.types.UnitType;

public class ConstructionSitePerceiver extends UnitPerceiver {
    
    public ConstructionSitePerceiver(JNIBWAPI api, Unit unit) {
        super(api,unit);
    }

   

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        jnibwapi.Map map = api.getMap();

  
        int mapWidth = map.getSize().getBX(); 
        int mapHeight = map.getSize().getBY();
        
        ArrayList<Point> illegals = new ArrayList<>();
        for (Unit u : api.getNeutralUnits()) {
            if (UnitTypesEx.isResourceType(u.getType())) {
                illegals.add(new Point(u.getTilePosition().getBX(), u.getTilePosition().getBY()));
            }
        }
        
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Position p = new Position(x, y, Position.PosType.BUILD);
                
                boolean buildable = api.canBuildHere(unit, p, unit.getType(), true);
                boolean explored = api.isExplored(p);
                if (buildable && explored) {
                    Point possible = new Point(x, y);
                    boolean add = true;
                    for (Point illegal : illegals) {
                        if (illegal.distance(possible) < 5) {
                            add = false;
                            break;
                        }
                    }
                    
                    if (add)
                        percepts.add(new ConstructionSitePercept(possible.x,possible.y));
                }
            }
        }

		// TODO: Locations near minerals and geysers are not buildable
        return percepts;
    }
}
