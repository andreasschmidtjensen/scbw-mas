+!spot("Vespene Geyser")
	:	vespeneGeyser(Id, _, _, BX, BY) &
		not spottedVespeneGeyser(Id,BX,BY)
	<-	.broadcast(tell,spottedVespeneGeyser(Id,BX,BY));+spottedVespeneGeyser(Id,BX,BY);+spot("Vespene Geyser").
+!spot("Vespene Geyser") <-.wait(200); !!spot("Vespene Geyser").

+!spot("Enemy Base")
	: 	enemy(Type,Id,WX,WY,_,_) &
		isBuilding(Type) &
		not lastSpottedEnemy(Id,WX,WY)
	<-	 .broadcast(tell,lastSpottedEnemy(Id,WX,WY)); +lastSpottedEnemy(Id,WX,WY);+spot("Enemy Base").
+!spot("Enemy Base") <-.wait(200).
-!spot("Enemy Base") <-.wait(200).

+!scouting
	:	spot("Enemy Base")
	<-	+scouting.	
+!scouting
	:	friendly(Name, "Terran Command Center", _, ComX, ComY, _, _ ) &
		position(MyX,MyY) &
		distance(MyX,MyY,ComX,ComY,D) &
		.findall([Name,OtherX,OtherY,OtherD], (friendly(Name, "Terran Marine", _, OtherX, OtherY, _, _) & distance(OtherX,OtherY,ComX,ComY,OtherD) & OtherD > D), []) &
		map(MapWidth,MapHeight)& 
		.random(Rand1)& X = Rand1 * MapWidth * 4 &
		.random(Rand2)& Y = Rand2 * MapHeight * 4 
	<-	move(X,Y); .wait(2500). 
	
+!scouting  <-.wait(200).
-!scouting  <-.wait(200).
