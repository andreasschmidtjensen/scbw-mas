package eisbw.actions;

import java.util.HashMap;
import java.util.Map;
import jnibwapi.JNIBWAPI;

public class ActionProvider {

    private final Map<String, StarcraftAction> actions;

    public ActionProvider() {
        this.actions = new HashMap<>();
    }

    public StarcraftAction getAction(String actionName) {
        return actions.get(actionName);
    }

    public void loadActions(JNIBWAPI api) {
        actions.put("gather", new Gather(api));
        actions.put("train", new Train(api));
        actions.put("build", new Build(api));
        actions.put("attack", new Attack(api));
        actions.put("move", new Move(api));
        actions.put("use", new Use(api));
        actions.put("useOnPosition", new UseOnPosition(api));
        actions.put("useOnTarget", new UseOnTarget(api));
        actions.put("attackMove", new AttackMove(api));
    }
}
