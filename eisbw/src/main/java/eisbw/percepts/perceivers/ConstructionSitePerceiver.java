package eisbw.percepts.perceivers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import jnibwapi.types.RaceType.RaceTypes;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.ConstructionSitePercept;

public class ConstructionSitePerceiver extends UnitPerceiver {

  public ConstructionSitePerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();
    jnibwapi.Map map = api.getMap();

    int mapWidth = map.getSize().getBX();
    int mapHeight = map.getSize().getBY();

    ArrayList<Point> illegals = new ArrayList<>();
    for (Unit u : api.getNeutralUnits()) {
      if (UnitTypesEx.isResourceType(u.getType()) && u.isExists()) {
        illegals.add(new Point(u.getTilePosition().getBX(), u.getTilePosition()
            .getBY()));
      }
    }
    for (int x = 0; x < mapWidth; x++) {
      for (int y = 0; y < mapHeight; y++) {
        Position p = new Position(x, y, Position.PosType.BUILD);
        boolean buildable = api.canBuildHere(unit, p,
            UnitType.UnitTypes.Terran_Command_Center, true);

        //boolean zergBuildable = api.canBuildHere(unit, p, UnitType.UnitTypes.Zerg_Hatchery, true);
        boolean explored = api.isExplored(p);
        //boolean hasCreep = api.hasCreep(p);
        if (buildable && explored && api.getSelf().getRace().equals(RaceTypes.Terran)) {
          Point possible = new Point(x, y);
          boolean add = true;
          for (Point illegal : illegals) {
            if (illegal.distance(possible) < 10) {
              add = false;
              break;
            }
          }

          if (add)
            percepts.add(new ConstructionSitePercept(possible.x, possible.y));
        } /*else if (zergBuildable && hasCreep) {
          
            Point possible = new Point(x, y);
            boolean add = true;
            for (Point illegal : illegals) {
              if (illegal.distance(possible) < 10) {
                add = false;
                break;
              }
            }

            if (add)
              percepts.add(new ZergConstructionSitePercept(possible.x, possible.y));
        } else {
        	if(api.canBuildHere(unit, p, UnitType.UnitTypes.Protoss_Stargate, true)) {
        		Point possible = new Point(x, y);
                boolean add = true;
                for (Point illegal : illegals) {
                  if (illegal.distance(possible) < 10) {
                    add = false;
                    break;
                  }
                }

                if (add)
                	percepts.add(new ProtossConstructionSitePercept(possible.x, possible.y));
        	}
        	
        	if(api.canBuildHere(unit, p, UnitType.UnitTypes.Protoss_Pylon, true)) {
        		Point possible = new Point(x, y);
                boolean add = true;
                for (Point illegal : illegals) {
                  if (illegal.distance(possible) < 10) {
                    add = false;
                    break;
                  }
                }

                if (add)
                	percepts.add(new PylonConstructionSitePercept(possible.x, possible.y));
        	}
        }*/
      }
    }

    // TODO: Locations near minerals and geysers are not buildable
    return percepts;
  }
}
