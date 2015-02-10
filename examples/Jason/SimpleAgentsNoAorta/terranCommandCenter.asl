{ include("generalKnowledge.asl") } 
{ include("trainer.asl") }

cost("Terran SCV", 50, 0, 2).
+gameStart <- !!maintainMentalState; !!train("Terran SCV", 5).


