{ include("generalKnowledge.asl") } 
{ include("trainer.asl") }
ignored(commander).

cost("Terran Marine", 50, 0, 2).
cost("Terran Firebat", 50, 25, 2).
cost("Terran Medic", 50, 25, 2).

+gameStart <- !!maintainMentalState; !!train("Terran Marine", 1); !!train("Terran Firebat", 1); !!train("Terran Medic", 1).
