package eisbw.units;

import eisbw.*;
import eisbw.percepts.perceivers.*;
import eisbw.units.terran.*;
import eisbw.units.terran.buildings.*;
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

        String un = unit.getType().getName();
        if (un.equals(UnitTypes.Terran_Command_Center.getName())) {
            perceptGenerators.add(new AvailableResourcesPerceiver(api));
            perceptGenerators.add(new QueuePerceiver(this.api, unit));
            perceptGenerators.add(new IdleWorkersPerceiver(api, util));
            return new CommandCenter(api, unit, perceptGenerators);
        } else if (un.equals(UnitTypes.Terran_Barracks.getName())) {
            perceptGenerators.add(new QueuePerceiver(this.api, unit));
            return new Barracks(api, unit, perceptGenerators);
        } else if (un.equals(UnitTypes.Terran_SCV.getName())) {
            perceptGenerators.add(new BuilderUnitPerceiver(api, unit));
            perceptGenerators.add(new GathererUnitPerceiver(api, unit));
            return new SCV(api, unit, perceptGenerators);
        } else if (un.equals(UnitTypes.Terran_Marine.getName())) {
            perceptGenerators.add(new StimUnitPerceiver(api, unit));
            perceptGenerators.add(new AttackingUnitsPerceiver(api));
            return new Marine(api, unit, perceptGenerators);
        } else {
            return new StarcraftUnit(api, unit, perceptGenerators);
        }
    }
}
