+gameStart <- !move.

+!move 
	:	position(MyX,MyY) &
		.findall([D,X,Y], (chokepoint(X,Y) & jia.tileDistance(MyX,MyY,X,Y,D)), L) &
		.min(L, [_,X,Y])
	<-	move(X,Y).
	
+!charge 
	:	lastSpottedEnemy(X,Y)
	<-	move(X,Y).
