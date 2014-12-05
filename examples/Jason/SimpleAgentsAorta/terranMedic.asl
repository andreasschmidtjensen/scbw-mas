{ include("groundUnit.asl") }
+!heal 
	:	lastSpottedEnemy(X,Y)
	<-	move(X,Y).
	
+!spotVespeneGeyser
	:	vespeneGeyser(_, _, _, _, _)
	<-	+spotVespeneGeyser.
+!spotVespeneGeyser <-.wait(200); !!spotVespeneGeyser.
-!spotVespeneGeyser <-.wait(200); !!spotVespeneGeyser.

+!spotEnemy
	:	enemy(Id,_,_,X,Y) &
		.findall(Name, agent(Name),Recipients)
	<-	+spotEnemy; .send(Recipients, tell, lastSpottedEnemy(Id,X,Y)).
+!spotEnemy <-.wait(200).
-!spotEnemy <-.wait(200).
