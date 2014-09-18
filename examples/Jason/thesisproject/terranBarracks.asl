!reason.

+!reason 
	: queue(Q) 
	& Q < 2
	& minerals(M) & M >= 50 
	& supply(X,Y) & X < Y 
	<- train("Terran_Marine"); .wait(500); !reason.
+!reason <- .wait(1000); !reason.
