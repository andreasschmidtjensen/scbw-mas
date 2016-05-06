package eisbw;

import eis.iilang.Percept;
import eisbw.percepts.GameStartPercept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.UnitPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import eisbw.percepts.perceivers.TotalResourcesPerceiver;
import eisbw.units.StarcraftUnit;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Game {

  private volatile Map<String, LinkedList<Percept>> percepts;
  private Units units;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the environment
   */
  public Game(StarcraftEnvironmentImpl environment) {
    units = new Units(environment);
    percepts = new HashMap<String, LinkedList<Percept>>();
  }

  /**
   * Updates the percepts.
   * 
   * @param bwapi
   *          - the game bridge
   */
  public void update(JNIBWAPI bwapi) {
    Map<String, LinkedList<Percept>> unitPerceptHolder = new HashMap<String, LinkedList<Percept>>();
    LinkedList<Percept> perceptHolder = getPercepts(bwapi);

    Map<String, StarcraftUnit> unitList = units.getStarcraftUnits();
    for (Entry<String, StarcraftUnit> unit : unitList.entrySet()) {
      LinkedList<Percept> thisUnitPercepts = new LinkedList<Percept>(perceptHolder);
      thisUnitPercepts.addAll(unit.getValue().perceive());
      
      unitPerceptHolder.put(unit.getKey(), thisUnitPercepts);
    }
    

    percepts = unitPerceptHolder;
    System.out.println(percepts);
  }
  
  private LinkedList<Percept> getPercepts(JNIBWAPI bwapi) {
    LinkedList<Percept> perceptHolder = new LinkedList<Percept>();
    perceptHolder.addAll(new TotalResourcesPerceiver(bwapi).perceive());
    perceptHolder.addAll(new ConstructionSitePerceiver(bwapi).perceive());

    Map<UnitType, Integer> count = new HashMap<>();
    for (Unit myUnit : bwapi.getMyUnits()) {
      UnitType unitType = myUnit.getType();
      if (!count.containsKey(unitType)) {
        count.put(unitType, 0);
      }
      count.put(unitType, count.get(unitType) + 1);
    }
    for (UnitType unitType : count.keySet()) {
      perceptHolder.add(new UnitPercept(unitType.getName(), count.get(unitType)));
    }

    for (Unit u : bwapi.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept mineralfield = new VespeneGeyserPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);

        }
      }
    }

    perceptHolder.add(new GameStartPercept());
    return perceptHolder;
  }

  /**
   * Get the percepts of this unit.
   * 
   * @param entity
   *          - the name of the unit
   * @return the percepts
   */
  public LinkedList<Percept> getPercepts(String entity) {
    if (percepts.containsKey(entity)) {
      return percepts.get(entity);
    }
    return new LinkedList<Percept>();
  }

  public Units getUnits() {
    return units;
  }

}
