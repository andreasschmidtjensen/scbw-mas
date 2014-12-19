+gameStart <- !move.

distance(MyX,MyY,X,Y,D)
	:-	D = math.sqrt((MyX-X)**2 + (MyY-Y)**2).
	
closest("Enemy", ClosestId)
	:-	position(MyX,MyY) &
		.findall([Dist,Id],(enemy(_,Id,WX,WY,_,_)&distance(MyX,MyY,WX,WY,Dist)),L) &
		.min(L,[ClosestDist,ClosestId]).
		
closestToBase("Enemy", ClosestId, ClosestDist)
	:-	friendly(_, "Terran Command Center", Id, X, Y, _, _) & 
		.findall([Dist,EId],(enemy(_,EId,WX,WY,_,_)&distance(WX,WY,X,Y,Dist)),L) &
		.min(L,[ClosestDist,ClosestId]).
		

+!charge
	:	closest("Enemy", ClosestId) 
	<-	attack(ClosestId); .wait(200); !!charge.
+!charge 
	:	lastSpottedEnemy(Id,X,Y)
	<-	move(X,Y); .wait(2000); attack(Id).
-!charge 
	<-	.wait(200); !!charge.
	
+!defend 
	:	closestToBase("Enemy", ClosestId, ClosestDist) &
		ClosestDist < 125
	<-	attack(ClosestId).
-!defend 
	<-	.wait(200); !!defend.
	
+!move 
	:	position(MyX,MyY) &
		.findall([D,X,Y], (chokepoint(X,Y) & jia.tileDistance(MyX,MyY,X,Y,D)), L) &
		.min(L, [_,X,Y])
	<-	move(X,Y).
	
-!move <- .wait(200).
