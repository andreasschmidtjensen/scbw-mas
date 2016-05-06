package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.LinkedList;

public class AttackMove extends StarcraftAction {

  public AttackMove(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 2 && parameters.get(0) 
        instanceof Numeral && parameters.get(1) instanceof Numeral;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    UnitType unitType = unit.getType();
    return unitType.isCanMove() && unitType.isAttackCapable();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int xpos = ((Numeral) parameters.get(0)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(1)).getValue().intValue();
    unit.attack(new Position(xpos, ypos, Position.PosType.BUILD), false);
  }

  @Override
  public String toString() {
    return "attack(x, y)"; 
  }
}
