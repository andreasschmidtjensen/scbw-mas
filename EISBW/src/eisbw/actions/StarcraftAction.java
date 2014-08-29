package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.BWApiUtility;
import jnibwapi.*;

public abstract class StarcraftAction {

    protected JNIBWAPI api;
    protected BWApiUtility utility;

    public StarcraftAction(JNIBWAPI api) {
        this.api = api;
        this.utility = new BWApiUtility(api);
    }

    public abstract boolean isValid(Action action);

    public abstract boolean canExecute(Unit unit, Action action);

    public abstract void execute(Unit unit, Action action) throws ActException;

    @Override
    public abstract String toString();
}
