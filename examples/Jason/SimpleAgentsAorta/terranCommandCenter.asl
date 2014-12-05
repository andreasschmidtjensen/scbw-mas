cost("Terran SCV", 50, 0, 2).

+!trainTerranSCV
	<-	!train("Terran SCV").
	
+!train(Unit)
	:	unit(Unit, Count) &
		Count >= 5
	<-	+trainTerranSCV.
+!train(Unit) 
	: 	cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max
	<- train(Unit).
+!train(_).
