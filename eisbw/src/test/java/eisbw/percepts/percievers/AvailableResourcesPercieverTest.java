package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.AvailableResourcesPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AvailableResourcesPercieverTest {

  private AvailableResourcesPerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Player player;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(bwapi.getSelf()).thenReturn(player);
    when(player.getMinerals()).thenReturn(50);
    when(player.getGas()).thenReturn(50);
    when(player.getSupplyTotal()).thenReturn(50);
    when(player.getSupplyUsed()).thenReturn(50);
    perciever = new AvailableResourcesPerceiver(bwapi);
  }
  
  @Test
  public void test_MineralPercept() {
    assertEquals("minerals", perciever.perceive().get(0).getName());
    assertEquals("50",
        perciever.perceive().get(0).getParameters().get(0).toProlog());
  }
  
  @Test
  public void test_GasPercept() {
    assertEquals("gas", perciever.perceive().get(1).getName());
    assertEquals("50",
        perciever.perceive().get(1).getParameters().get(0).toProlog());
  }
  
  @Test
  public void test_SupplyPercept() {
    assertEquals("supply", perciever.perceive().get(2).getName());
    assertEquals("50",
        perciever.perceive().get(2).getParameters().get(0).toProlog());
    assertEquals("50",
        perciever.perceive().get(2).getParameters().get(1).toProlog());
  }

}
