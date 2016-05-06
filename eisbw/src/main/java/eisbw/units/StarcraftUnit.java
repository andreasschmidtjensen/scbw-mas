package eisbw.units;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class StarcraftUnit {

  protected final Unit unit;
  protected final JNIBWAPI api;
  protected final List<IPerceiver> perceivers;

  /**
   * A starcraft unit with perceivers.
   * @param api - the API to get data.
   * @param unit - the unit.
   * @param perceivers - list with perceivers to percept from.
   */
  public StarcraftUnit(JNIBWAPI api, Unit unit, List<IPerceiver> perceivers) {
    this.api = api;
    this.unit = unit;
    this.perceivers = perceivers;
  }

  /**
   * Percept this units' percepts.
   * @return - a list of percepts.
   */
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<Percept>();
    for (IPerceiver perceiver : this.perceivers) {
      percepts.addAll(perceiver.perceive());
    }
    return percepts;
  }
}
