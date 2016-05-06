package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.BuildUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BuildUnitPercieverTest {

  private BuildUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(unit.isBeingConstructed()).thenReturn(true);
    when(unit.getBuildUnit()).thenReturn(unit);
    when(unit.getID()).thenReturn(1);
    perciever = new BuildUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    assertEquals("buildUnit", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }

}
