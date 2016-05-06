package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.AddonPerceiver;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddonPercieverTest {

  private AddonPerceiver addonPerciever;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unittype;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(unit.getAddon()).thenReturn(unit);
    when(unit.getType()).thenReturn(unittype);
    when(unittype.getName()).thenReturn("unitType");
    addonPerciever = new AddonPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    assertEquals("addon", addonPerciever.perceive().get(0).getName());
    assertEquals("unitType",
        addonPerciever.perceive().get(0).getParameters().get(0).toProlog());
  }
  
  @Test
  public void test_Null() {
    when(unit.getAddon()).thenReturn(null);
    assertEquals(0, addonPerciever.perceive().size());
  }

}
