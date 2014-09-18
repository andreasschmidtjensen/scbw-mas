package jia;

import eisbw.*;
import jason.asSemantics.*;
import jason.asSyntax.*;
import java.util.*;
import jnibwapi.*;
import jnibwapi.Unit;


public class center extends DefaultInternalAction {
    private final JNIBWAPI game;
    private final BWApiUtility util;
    
    public center() {
        super();
        this.game = BWAPIBridge.getGame();
        this.util = new BWApiUtility(game);
    }
    
    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        List<Term> unitIds = ((ListTerm) args[0]).getAsList();
//        int[] unitIdssss = game.getUnitsInWeaponRangeOfUnit(1, 0);
//        int[] unitIdsss = game.getUnitsInRadiusOfUnit(0, 500);
        if (unitIds.isEmpty()) {
            return false;
        }
        
        double totalX = 0;
        double totalY = 0;
        int count = unitIds.size();
        
        List<Integer> unitIdsSolved = new ArrayList<>();
        for(Term t : unitIds) {
            unitIdsSolved.add((int) ((NumberTerm)t).solve());
        }
        
        for (int id : unitIdsSolved) {
            Unit u = game.getUnit(id);
            totalX += u.getX();
            totalY += u.getY();
        }
        
        VarTerm x = (VarTerm) args[1];        
        VarTerm y = (VarTerm) args[2];

        un.unifies(x, new NumberTermImpl(totalX / count));        
        un.unifies(y, new NumberTermImpl(totalY / count));
        
        return true;
    }
}
