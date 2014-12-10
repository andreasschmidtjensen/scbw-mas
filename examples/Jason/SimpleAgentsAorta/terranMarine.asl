{ include("groundUnit.asl") } 

isBuilding("Terran Command Center").
isBuilding("Terran Barracks").
isBuilding("Terran Supply Depot").
isBuilding("Terran Academy").
isBuilding("Terran Engineering Bay").
isBuilding("Terran Refinery").
isBuilding("Terran Machine Shop").
isBuilding("Terran Bunker").
isBuilding("Terran Armory").
isBuilding("Terran Nuclear Silo").
isBuilding("Terran Missile Turret").
isBuilding("Terran Comsat Station").
isBuilding("Terran Factory").

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

+!spot(X)
	:	X = "Vespene Geyser" &
		vespeneGeyser(_, _, _, _, _)
	<-	+spot(X).
+!spot("Vespene Geyser") <-.wait(200).
-!spot("Vespene Geyser") <-.wait(200).

+!spot(X)
	: 	X = "Enemy Base" &	
		.print("Trying to spot enemy base") &
		enemy(Type,Id,WX,WY,_,_) &
		isBuilding(Type) &
		.print("Enemy Base ", Id)
	<-	+lastSpottedEnemy(Id,WX,WY); +spot(X).
+!spot("Enemy Base") <-.wait(200).
-!spot("Enemy Base") <-.wait(200).

/* bunker logic */