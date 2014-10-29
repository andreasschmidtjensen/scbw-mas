cost("Terran SCV", 50, 0, 1).

+gameStart <- !work.

+!work 
	: 	Unit = "Terran SCV" & 
		.findall(Unit, friendly(_, Unit, _, _, _, _, _), L) &
		.length(L, N) & N < 10 & queueSize(Q) & Q < 2
	<-	!train(Unit); .wait(1000); !!work.
+!work
	<-	.wait(1000); !!work.
	
+!train(Unit) 
	: 	cost(Unit, M, G, S) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ & 
		supply(C, Max) & C + S <= Max
	<- train(Unit).
+!train(_).
