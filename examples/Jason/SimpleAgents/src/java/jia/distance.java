package jia;

import eisbw.BWAPIBridge;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import jason.asSyntax.VarTerm;
import java.awt.Point;
import java.awt.geom.Point2D;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class distance extends DefaultInternalAction  {
    private final JNIBWAPI game;
    
    public distance() {
        super();
        this.game = BWAPIBridge.getGame();
    }

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        int unitId = (int) ((NumberTerm) args[0]).solve();
        int otherUnitId = (int) ((NumberTerm) args[1]).solve();
        Unit unit = this.game.getUnit(unitId);
        Unit otherUnit = this.game.getUnit(otherUnitId);
        
        Point2D p1 = new Point(unit.getX(), unit.getY());
        Point2D p2 = new Point(otherUnit.getX(), otherUnit.getY());
        
        double distance = p1.distance(p2);
        
        VarTerm d = (VarTerm) args[2];
        un.unifies(d, new NumberTermImpl(distance));
        
        return true;
    }
}
