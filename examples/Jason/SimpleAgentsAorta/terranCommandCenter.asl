cost("Terran SCV", 50, 0, 2).

+!train(Unit,Y)
	:	unit(Unit,Count) &
		Y = Count
	<-	+train(Unit,Y).
+!train(Unit,Y) 
	:	queueSize(Q) & Q < 3 & 
		not unit(Unit,_)
	<-	!train(Unit); .wait(1000); !!train(Unit,Y).
+!train(Unit,Y) 
	: 	queueSize(Q) & Q < 3 & 
		unit(Unit,Count) &
		Count < Y
	<-	!train(Unit); .wait(1000); !!train(Unit,Y).
+!train(Unit,Y) 
	<- .wait(1000); !!train(Unit,Y).
-!train(Unit,Y)  <- .wait(200); !!train(Unit,Y) .
+!train(Unit) 
	:	cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max
	<- train(Unit); .wait(200).
+!train(_).
