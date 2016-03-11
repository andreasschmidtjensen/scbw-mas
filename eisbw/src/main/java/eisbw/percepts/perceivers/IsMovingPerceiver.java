package eisbw.percepts.perceivers;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import eis.iilang.Percept;
import eisbw.percepts.FollowingPercept;
import eisbw.percepts.MovingPercept;

public class IsMovingPerceiver extends UnitPerceiver {
    public IsMovingPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
      ArrayList<Percept> percepts = new ArrayList<Percept>();
      
      if(unit.isMoving())
        percepts.add(new MovingPercept());
      
      if(unit.isFollowing())
        percepts.add(new FollowingPercept());
      
      return percepts;
    }
    
}