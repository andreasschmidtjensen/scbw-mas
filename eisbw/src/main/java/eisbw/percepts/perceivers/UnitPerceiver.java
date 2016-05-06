package eisbw.percepts.perceivers;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public abstract class UnitPerceiver extends Perceiver {

  protected final Unit unit;

  public UnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api);
    this.unit = unit;
  }
}
