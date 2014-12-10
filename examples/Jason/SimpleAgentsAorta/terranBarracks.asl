cost("Terran Marine", 50, 0, 2).
cost("Terran Firebat", 50, 25, 2).
cost("Terran Medic", 50, 25, 2).

canTrain(Unit) :- 
		queueSize(Q) & Q < 3 & 
		cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max.
		
//want("Terran Marine", 10).
//want("Terran Firebat", 5).
//want("Terran Medic", 3).
+gameStart <- !!maintainMentalState.

/* Test if researched before trying */
+!train(Unit,Y)
	:	unit(Unit,Count) &
		Count >= Y
	<-	+train(Unit,Y).
+!train(Unit,Y) 
	:	queueSize(Q) & Q < 3 & 
		not training &
		not unit(Unit,_) &
		canTrain(Unit) &
		.print(Q," ",M," ",MQ," ",G," ",GQ," ",S," ",C," ",Max)
	<-	+training; train(Unit); .wait(2000); -training; !!train(Unit,Y).
+!train(Unit,Y) 
	: 	not training &
		unit(Unit,Count) &
		Count < Y &
		canTrain(Unit) &
		.print(Q," ",M," ",MQ," ",G," ",GQ," ",S," ",C," ",Max)
	<-	+training; train(Unit); .wait(2000); -training; !!train(Unit,Y).
	
-!train(Unit,Y)  <- .wait(200); !!train(Unit,Y).

+!maintainMentalState
	:	train(Unit,Count) &
		not (unit(Unit,Count2) & Count2 >= Count)  &
		.print("removing train belief")
	<- -train(Unit,Count);  !!maintainMentalState.
+!maintainMentalState
	<- .wait(200); !!maintainMentalState.
-!maintainMentalState
	<- .wait(200); !!maintainMentalState.

