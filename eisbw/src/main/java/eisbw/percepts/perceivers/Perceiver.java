package eisbw.percepts.perceivers;

import jnibwapi.JNIBWAPI;

public abstract class Perceiver implements IPerceiver {

  protected final JNIBWAPI api;

  public Perceiver(JNIBWAPI api) {
    this.api = api;
  }
}
