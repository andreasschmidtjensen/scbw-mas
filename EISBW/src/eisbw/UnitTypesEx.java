package eisbw;

import jnibwapi.types.UnitType;

public class UnitTypesEx {
    
    public static boolean isResourceType(UnitType unitType) {
        switch (unitType.getName()) {
            case "Resource Mineral Field":
            case "Resource Mineral Field Type 2":
            case "Resource Mineral Field Type 3":
            case "Resource Vespene Geyser":
            case "Terran Refinery":
            case "Protoss Assimilator":
            case "Zerg Extractor":
                return true;
            default:
                return false;
        }
    }

    public static boolean isRefinery(UnitType unitType) {
        switch (unitType.getName()) {
            case "Terran Refinery":
            case "Protoss Assimilator":
            case "Zerg Extractor":
                return true;
            default:
                return false;
        }
    }
    
    public static boolean isVespeneGeyser(UnitType unitType) {
        switch (unitType.getName()) {
            case "Resource Vespene Geyser":
                return true;
            default:
                return false;
        }
    }

    public static boolean isMineralField(UnitType unitType) {
        switch (unitType.getName()) {
            case "Resource Mineral Field":
            case "Resource Mineral Field Type 2":
            case "Resource Mineral Field Type 3":
                return true;
            default:
                return false;
        }
    }
}
