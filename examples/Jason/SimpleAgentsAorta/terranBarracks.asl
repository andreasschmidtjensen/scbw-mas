cost("Terran Marine", 50, 0, 2).
cost("Terran Firebat", 50, 25, 2).
cost("Terran Medic", 50, 25, 2).

//want("Terran Marine", 10).
//want("Terran Firebat", 5).
//want("Terran Medic", 3).

//+gameStart <- !work.
/* Test if researched before trying */
+!trainTerranMedicX
	<-	!train("Terran Medic", 1).
+!trainTerranMarineX
	<-	!train("Terran Marine", 1).
+!trainTerranFirebatX
	<-	!train("Terran Firebat", 1).

+!train(Unit,Y)
	:	Unit = "Terran Marine" &
		unit(Unit,Count) &
		Y = Count
	<-	+trainTerranMarineX.
+!train(Unit,Y)
	:	Unit = "Terran Medic" &
		unit(Unit,Count) &
		Y = Count
	<-	+trainTerranMedicX.
+!train(Unit,Y)
	:	Unit = "Terran Firebat" &
		unit(Unit,Count) &
		Y = Count
	<-	+trainTerranFirebatX.
+!train(Unit,Y) 
	:	queueSize(Q) & Q < 3 & 
		not unit(Unit,_)
	<-	!train(Unit);.print("Training ", Unit); .wait(1000); !!train(Unit,Y).
+!train(Unit,Y) 
	: 	queueSize(Q) & Q < 3 & 
		unit(Unit,Count) &
		Count < Y
	<-	!train(Unit); .wait(1000); !!train(Unit,Y).
+!train(Unit,Y) 
	<- .wait(1000); !!train(Unit,Y).
-!train(Unit,Y)  <- .wait(200); !!train(Unit,Y) .
	
+!train(Unit) 
	: 	.print("unit1",Unit) &
		cost(Unit2, M, G, S) &
		.print("unit2",Unit2) &
		cost(Unit, M, G, S) & 
		.print(M, G, S) &
		minerals(MQ) & M <= MQ &
		.print("After minerals") &
		gas(GQ) & G <= GQ & 
		.print("After gas") &
		supply(C, Max) & C + S <= Max&
		.print("After supply")
	<- train(Unit).
+!train(_).
