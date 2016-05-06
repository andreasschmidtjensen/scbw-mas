package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.BuilderUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BuilderUnitPercieverTest {

  private BuilderUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(unit.isConstructing()).thenReturn(true);
    perciever = new BuilderUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    assertEquals("constructing", perciever.perceive().get(0).getName());
  }

}
