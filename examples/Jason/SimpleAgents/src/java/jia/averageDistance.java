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

public class averageDistance extends DefaultInternalAction {
    private final JNIBWAPI game;
    private final BWApiUtility util;
    
    public averageDistance() {
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
        
        VarTerm x = (VarTerm) args[1];
        
        if (unitIds.size() == 1) {
            un.unifies(x, new NumberTermImpl(0));
            return true;
        }
        
        double totalDistance = 0;
        
        List<Integer> unitIdsSolved = new ArrayList<>();
        for(Term t : unitIds) {
            unitIdsSolved.add((int) ((NumberTerm)t).solve());
        }
        
        int n = unitIdsSolved.size();
        
        for (int i = 0; i < n - 1; i++) {
            int t = unitIdsSolved.get(i);
            for (int j = i+1; j < n; j++) {
                int t1 = unitIdsSolved.get(j);
                totalDistance += util.distanceSq(t, t1);
            }
        }
        
        double numberOfDistances = n*(n-1)/2; // n + n-1 + n-2 + n-3 ...
        
        un.unifies(x, new NumberTermImpl(totalDistance / numberOfDistances));
        
        return true;
    }
}
