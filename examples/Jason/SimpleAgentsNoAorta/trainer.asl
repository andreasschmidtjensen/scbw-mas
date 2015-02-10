unitType("Terran Marine").
unitType("Terran SCV").
unitType("Terran Firebat").
unitType("Terran Medic").

canTrain(Unit) :- 
		queueSize(Q) & Q < 3 & 
		cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max.
		

+!train(Unit,Y)
	:	unit(Unit,Count) &
		Count >= Y 
	<-	+train(Unit,Y).
+!train(Unit,Y) 
	:	not training &
		not unit(Unit,_) &
		queueSize(Q) &
		Q < Y &
		canTrain(Unit)&
		.print("QueueSize: ", Q)
	<-	+training; train(Unit); .wait(2000); -training; !!train(Unit,Y).
+!train(Unit,Y) 
	: 	not training &
		unit(Unit,Count) &
		queueSize(Q) &
		Q+Count < Y &
		canTrain(Unit)&
		.print("QueueSize: ", Q, " Count: ",Count)
	<-	+training; train(Unit); .wait(2000); -training; !!train(Unit,Y).
	
-!train(Unit,Y)  <- .wait(200); !!train(Unit,Y).

+!maintainMentalState
	:	unitType(Unit) &
		not cost(Unit,_,_,_) &
		not ignored(train(Unit,_)) &
		.print("Updating ignored", ignored(train(Unit,X)))
	<-  +ignored(train(Unit,_));  !!maintainMentalState.
+!maintainMentalState
	:	train(Unit,Count) &
		not (unit(Unit,Count2) & Count2 >= Count)  &
		.print("removing train belief")
	<- -train(Unit,Count);  !!maintainMentalState.
+!maintainMentalState
	<- .wait(200); !!maintainMentalState.
-!maintainMentalState
	<- .wait(200); !!maintainMentalState.

