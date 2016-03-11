package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class AddonPercept extends Percept {

    public AddonPercept(String addon) {
        super(Percepts.Addon, new Identifier(addon));
    }

}
