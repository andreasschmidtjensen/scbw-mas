package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType;
import jnibwapi.types.UnitType;
import jnibwapi.types.RaceType.RaceTypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ConstructionSitePercieverTest {

  private ConstructionSitePerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private jnibwapi.Map map;
  @Mock
  private Position mapsize;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private Player player;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    ArrayList<Unit> neutrals = new ArrayList<>();
    neutrals.add(unit);
    when(unit.getType()).thenReturn(unitType);
    when(unit.isExists()).thenReturn(true);
    when(unit.getTilePosition()).thenReturn(mapsize);
    when(unitType.getName()).thenReturn("Resource Mineral Field");

    when(bwapi.canBuildHere(any(Position.class), any(UnitType.class), any(Boolean.class)))
        .thenReturn(true);
    when(bwapi.getSelf()).thenReturn(player);
    when(player.getRace()).thenReturn(RaceTypes.Terran);
    
    when(bwapi.getMap()).thenReturn(map);
    when(map.getSize()).thenReturn(mapsize);
    when(mapsize.getBX()).thenReturn(10);
    when(mapsize.getBY()).thenReturn(10);
    when(bwapi.getNeutralUnits()).thenReturn(neutrals);
    perciever = new ConstructionSitePerceiver(bwapi);
  }

  @Test
  public void test() {
    assertEquals("constructionSite", perciever.perceive().get(0).getName());
  }

}
