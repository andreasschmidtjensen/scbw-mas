package eisbw;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;

public class BWApiUtility {

    public JNIBWAPI bwapi;
    private final HashMap<String, UnitType> unitTypeMap = new HashMap<>();
    private final HashMap<String, TechType> techTypeMap = new HashMap<>();
    private final HashMap<String, UpgradeType> upgradeTypeMap = new HashMap<>();

    public BWApiUtility(JNIBWAPI api) {
        this.bwapi = api;
    }

    public double distanceSq(int unitId, int otherUnitId) {
        Unit unit = this.bwapi.getUnit(unitId);
        Unit otherUnit = this.bwapi.getUnit(otherUnitId);
        Point2D p1 = new Point(unit.getPosition().getBX(), unit.getPosition().getBY());
        Point2D p2 = new Point(otherUnit.getPosition().getBX(), otherUnit.getPosition().getBY());

        return p1.distanceSq(p2);
    }

    public String getUnitName(Unit u) {
		String name = (u.getType().getName() + u.getID()).replace("_", "").replace(" ", "");
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }
	
	public String getEISUnitType(Unit u) {
		String type = u.getType().getName().replace(" ", "");
		type = type.substring(0, 1).toLowerCase() + type.substring(1);
		return type;
	}
	

    public UnitType getUnitType(String type) {
        if (this.unitTypeMap.isEmpty()) {
            for (UnitType ut : UnitTypes.getAllUnitTypes()) {
                unitTypeMap.put(ut.getName(), ut);
            }
        }
        
        return this.unitTypeMap.get(type);
    }

    public TechType getTechType(String type) {
        if (this.techTypeMap.isEmpty()) {
            for (TechType tt : TechTypes.getAllTechTypes()) {
                techTypeMap.put(tt.getName(), tt);
            }
        }
                
        return this.techTypeMap.get(type);
    }
    
    public UpgradeType getUpgradeType(String type) {
        if (this.upgradeTypeMap.isEmpty()) {
            for (UpgradeType tt : UpgradeTypes.getAllUpgradeTypes()) {
                upgradeTypeMap.put(tt.getName(), tt);
            }
        }
                
        return this.upgradeTypeMap.get(type);
    }
    
}
