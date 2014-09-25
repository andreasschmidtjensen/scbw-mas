cost("Terran Marine", 50, 0, 1).
cost("Terran Firebat", 50, 25, 1).
cost("Terran Medic", 50, 25, 1).

want("Terran Marine", 10).
want("Terran Firebat", 5).
want("Terran Medic", 3).

+gameStart <- !work.
/* Test if researched before trying */
+!work 
	: 	queueSize(Q) & Q < 3 & 
		.findall(Unit, (want(Unit, X) & .findall(_, friendly(_, Unit, _, _, _, _, _), L) & .length(L, N) & N < X), L) &
		.shuffle(L, S) & S = [Unit|_]
	<-	!train(Unit); .wait(1000); !!work.
+!work
	<- .wait(1000); !!work.
-!work <- .wait(200); !work.
	
+!train(Unit) 
	: 	cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max
	<- train(Unit).
+!train(_).
