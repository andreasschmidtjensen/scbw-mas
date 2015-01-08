{ include("generalKnowledge.asl") } 
{ include("trainer.asl") }

cost("Terran SCV", 50, 0, 2).

+!trainArmy
	:	train("Terran SCV", _) &
		train("Terran Marine",_) &
		train("Terran Firebat",_) &
		train("Terran Medic",_)
	<-	+trainArmy.
+!trainArmy
	<-	.wait(1000).
-!trainArmy
	<-	.wait(1000).


