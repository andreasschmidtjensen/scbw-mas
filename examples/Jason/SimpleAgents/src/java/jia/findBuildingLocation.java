/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jia;

import eisbw.*;
import jason.asSemantics.*;
import jason.asSyntax.*;
import java.awt.*;
import java.util.*;
import java.util.logging.*;
import jnibwapi.*;
import jnibwapi.types.*;

/**
 *
 * @author Andreas Schmidt Jensen <andreas.s.jensen@gmail.com>
 */
public class findBuildingLocation extends DefaultInternalAction {

    private static final Logger logger = Logger.getLogger(findBuildingLocation.class.getName());
    private final BWApiUtility utility;
    private final JNIBWAPI game;
    
    public findBuildingLocation() {
        super();
        this.game = BWAPIBridge.getGame();
        this.utility = new BWApiUtility(this.game);
    }

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        JNIBWAPI game = BWAPIBridge.getGame();
        int unitId = (int) ((NumberTerm) args[0]).solve();
        String type = ((StringTerm) args[1]).getString();
        int unitTypeId = utility.getUnitType(type).getID();
        UnitType unitType = game.getUnitType(unitTypeId);
        Unit unit = game.getUnit(unitId);

        Point location = findBuildingLocation(unit, unitType);

        if (location != null) {
            VarTerm x = (VarTerm) args[2];
            VarTerm y = (VarTerm) args[3];

            un.unifies(x, new NumberTermImpl(location.x));
            un.unifies(y, new NumberTermImpl(location.y));

            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param type
     * @param workerID
     * @param workerX - tile coordinates
     * @param workerY - tile coordinates
     * @return
     */
    public Point findBuildingLocation(Unit unit, UnitType type) {
        Point location = null;

        JNIBWAPI game = BWAPIBridge.getGame();

//		TODO Unit builder requiers more space to ensure units can come out) {
        ArrayList<Point> locations = getBuildLocations(game, type, unit);

        double closest = Double.MAX_VALUE;

        for (Point point : locations) {
            double dx = point.x - unit.getTileX();
            double dy = point.y - unit.getTileY();
            double distance = dx * dx + dy * dy;

            if (distance < closest) {
                closest = distance;
                location = point;
            }
        }

        return location;
    }

    public ArrayList<Point> getBuildLocations(JNIBWAPI game, UnitType unitType, Unit worker) {
        ArrayList<Point> locations = new ArrayList<>();
        int mapHeight = game.getMap().getHeight();
        int mapWidth = game.getMap().getWidth();
        
        ArrayList<Point> illegals = new ArrayList<>();
        for (Unit u : game.getNeutralUnits()) {
            if (UnitTypesEx.isResourceType(u.getType())) {
                illegals.add(new Point(u.getTileX(), u.getTileY()));
            }
        }
        
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Position p = new Position(x, y, Position.PosType.BUILD);
                
                boolean buildable = game.canBuildHere(worker, p, unitType, true);
                boolean explored = game.isExplored(p);
                if (buildable && explored) {
                    Point possible = new Point(x, y);
                    boolean add = true;
                    for (Point illegal : illegals) {
                        if (illegal.distance(possible) < 10.1) {
                            add = false;
                            break;
                        }
                    }
                    
                    if (add)
                        locations.add(possible);
                }
            }
        }

		// TODO: Locations near minerals and geysers are not buildable
        return locations;
    }

}
