package eisbw;

import jnibwapi.types.UnitType;

public class UnitTypesEx {

  /**
   * Check if a unit is a resource.
   * 
   * @param unitType
   *          - the type of the unit.
   * @return true when the unit is a resource, else false.
   */
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
    return unitType.isRefinery();
  }

  /**
   * Check if a unit is a command center.
   * @param unitType the type of the unit.
   * @return true iff the unit is a command center, else false.
   */
  public static boolean isCommandCenter(UnitType unitType) {
    switch (unitType.getName()) {
      case "Terran Refinery":
      case "Protoss Assimilator":
      case "Zerg Extractor":
        return true;
      default:
        return false;
    }
  }

  /**
   * Check if a unit is a vespene geyser.
   * @param unitType - the type of the unit.
   * @return true iff the unit is a vespene geyser.
   */
  public static boolean isVespeneGeyser(UnitType unitType) {
    switch (unitType.getName()) {
      case "Resource Vespene Geyser":
        return true;
      default:
        return false;
    }
  }

  /**
   * Check if a unit is a mineral field.
   * @param unitType - the type of the unit.
   * @return true iff the unit is a vespene geyser.
   */
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

  /**
   * Check if a unit can do upgrades.
   * @param unitType the type of the unit.
   * @return true iff the unit can upgrade.
   */
  public static boolean isUpgradeCapable(UnitType unitType) {
    switch (unitType.getName()) {
      case "Terran Engineering Bay":
      case "Terran Academy":
        return true;
      default:
        return false;
    }
  }
}
