package eisbw.units;

import eisbw.*;
import eisbw.percepts.perceivers.*;
import java.util.*;
import jnibwapi.*;
import jnibwapi.types.UnitType.UnitTypes;

public class StarcraftUnitFactory {

    private final JNIBWAPI api;
    private final BWApiUtility util;

    public StarcraftUnitFactory(JNIBWAPI api, BWApiUtility util) {
        this.api = api;
        this.util = util;
    }

    public StarcraftUnit Create(Unit unit) {
        ArrayList<IPerceiver> perceptGenerators = new ArrayList<>();
        perceptGenerators.add(new GenericUnitPerceiver(api, unit));
        perceptGenerators.add(new MapPerceiver(api));
        perceptGenerators.add(new EnemyPerceiver(api));
        perceptGenerators.add(new PlayerUnitsPerceiver(api, util));

		if (unit.getType().isBuilding()) {
            perceptGenerators.add(new AvailableResourcesPerceiver(api));
            perceptGenerators.add(new QueuePerceiver(this.api, unit));			
		}
		if (unit.getType().isAttackCapable()) {
            perceptGenerators.add(new AttackingUnitsPerceiver(api));			
		}
		if (unit.getType().isWorker()) {
            perceptGenerators.add(new AvailableResourcesPerceiver(api));
            perceptGenerators.add(new BuilderUnitPerceiver(api, unit));
            perceptGenerators.add(new GathererUnitPerceiver(api, unit));			
		}
		
        String un = unit.getType().getName();
        if (un.equals(UnitTypes.Terran_Command_Center.getName())) {
            perceptGenerators.add(new IdleWorkersPerceiver(api, util));
        } else if (un.equals(UnitTypes.Terran_Marine.getName())) {
            perceptGenerators.add(new StimUnitPerceiver(api, unit));
        }
		
		return new StarcraftUnit(api, unit, perceptGenerators);
    }
}
