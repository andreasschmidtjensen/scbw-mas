package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.EnemyPercept;
import java.util.*;
import jnibwapi.*;

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
                    unit.getPosition().getWX(), unit.getPosition().getWY(), 
                    unit.getPosition().getBX(), unit.getPosition().getBY()));
        }

        return percepts;
    }
}
