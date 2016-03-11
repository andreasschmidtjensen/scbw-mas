package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.EnemyPercept;

public class EnemyPerceiver extends Perceiver {

    public EnemyPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        List<Unit> enemies = api.getEnemyUnits();

        for (Unit unit : enemies) {
            percepts.add(new EnemyPercept(unit.getType().getName(), unit.getID(),
                    unit.getHitPoints(), unit.getShields(), unit.getType().isFlyer(),
                    unit.getPosition().getWX(), unit.getPosition().getWY(), 
                    unit.getPosition().getBX(), unit.getPosition().getBY()));
        }

        return percepts;
    }
}
