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
        actions.put("attack/1", new Attack(api));
        actions.put("attack/2", new AttackMove(api));
        actions.put("build/3", new Build(api));
        actions.put("gather/1", new Gather(api));
        actions.put("move/2", new Move(api));
        actions.put("train/1", new Train(api));
        actions.put("stop/0", new Stop(api));
        actions.put("use/1", new Use(api));
        actions.put("use/2", new UseOnTarget(api));
        actions.put("use/3", new UseOnPosition(api));
    }
}
