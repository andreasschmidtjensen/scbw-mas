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
import java.util.ArrayList;
import java.util.List;
import jnibwapi.JNIBWAPI;

public class weakest extends DefaultInternalAction {
    private final JNIBWAPI game;
    private final BWApiUtility util;
    
    public weakest() {
        super();
        this.game = BWAPIBridge.getGame();
        this.util = new BWApiUtility(game);
    }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        List<Term> unitIds = ((ListTerm) args[0]).getAsList();
        
        if (unitIds.isEmpty()) {
            return false;
        }
        
        int weakestId = (int) ((NumberTerm)unitIds.get(0)).solve();
        
        List<Integer> unitIdsSolved = new ArrayList<>();
        for(Term t : unitIds) {
            unitIdsSolved.add((int) ((NumberTerm)t).solve());
        }
        
        double highestSumDistance = 0;
        for(Integer t : unitIdsSolved) {
            
            double tSumDistance = 0;
            
            for(Integer t1 : unitIdsSolved) {
                tSumDistance +=  util.distanceSq(t, t1);
            }
            
            if (tSumDistance > highestSumDistance) {
                highestSumDistance = tSumDistance;
                weakestId = t;
            }
        }
        
        VarTerm x = (VarTerm) args[1];
        un.unifies(x, new NumberTermImpl(weakestId));
        
        return true;
    }
}
