package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class UpgradePercept extends Percept {

    public UpgradePercept(String upgrade) {
        super(Percepts.Upgrade, new Identifier(upgrade));
    }

}
