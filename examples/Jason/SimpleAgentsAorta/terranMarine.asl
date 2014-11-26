{ include("groundUnit.asl") } 

+!scouting
	:	friendly(Name, "Terran Command Center", _, ComX, ComY, _, _ ) &
		position(MyX,MyY) &
		jia.tileDistance(MyX,MyY,ComX,ComY,D) &
		.findall([Name,OtherX,OtherY,OtherD], (friendly(Name, _, _, OtherX, OtherY, _, _)), M) &
		map(MapWidth,MapHeight)& 
		M = [] &
		.random(Rand1)& X = Rand1 * MapWidth &
		.random(Rand2)& Y = Rand2 * MapHeight 
	<-	move(X,Y). 
	
+!scouting  <-.wait(200).
-!scouting  <-.wait(200).

/* bunker logic */