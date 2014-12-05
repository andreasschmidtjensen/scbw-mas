{ include("groundUnit.asl") } 

+!spotEnemy
	:	enemy(Id,_,_,X,Y) &
		.findall(Name, agent(Name),Recipients)
	<-	+spotEnemy; .send(Recipients, tell, lastSpottedEnemy(Id,X,Y)).
+!spotEnemy <-.wait(200).
-!spotEnemy <-.wait(200).
