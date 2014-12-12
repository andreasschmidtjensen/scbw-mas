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
		.print("Enemy Base ", Type) &
		isBuilding(Type) &
		.print("Enemy Base ", Id)
	<-	 .broadcast(tell,lastSpottedEnemy(Id,WX,WY)); +spot(X);+lastSpottedEnemy(Id,WX,WY).
+!spot("Enemy Base") <-.wait(200).
-!spot("Enemy Base") <-.wait(200).

/* bunker logic */