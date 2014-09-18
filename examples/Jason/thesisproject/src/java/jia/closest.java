package jia;

import eisbw.BWAPIBridge;
import eisbw.BWApiUtility;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;
import jason.asSyntax.VarTerm;
import java.util.List;
import jnibwapi.JNIBWAPI;

public class closest extends DefaultInternalAction  {
    private final JNIBWAPI game;
    private final BWApiUtility util;
    
    public closest() {
        super();
        this.game = BWAPIBridge.getGame();
        this.util = new BWApiUtility(game);
    }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        int unitId = (int) ((NumberTerm) args[0]).solve();
        List<Term> unitIds = ((ListTerm) args[1]).getAsList();
        
        if (unitIds.isEmpty()) {
            return false;
        }
        
        int closestId = (int) ((NumberTerm)unitIds.get(0)).solve();
        double closestDistance = util.distanceSq(unitId, closestId);
        
        for (Term term : unitIds) {
            int id = (int) ((NumberTerm)term).solve();
            double distance = util.distanceSq(id, unitId);
            
            if (distance < closestDistance) {
                closestDistance = distance;
                closestId = id;
            }
        }
        
        VarTerm x = (VarTerm) args[2];
        un.unifies(x, new NumberTermImpl(closestId));
        
        return true;
    }
}
