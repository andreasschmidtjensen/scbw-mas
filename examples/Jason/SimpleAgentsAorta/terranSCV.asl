cost("Terran Supply Depot", 100, 0).
cost("Terran Barracks", 150, 0).
cost("Terran Academy", 150, 0).
cost("Terran Engineering Bay", 125, 0).
cost("Terran Bunker", 100, 0).
cost("Terran Refinery", 100, 0).

condition("Terran Supply Depot") :- supply(C, Max) & Max - C < 3.
condition("Terran Barracks") :- .findall(_, friendly(_, "Terran Barracks", _, _, _, _, _), L) & .length(L,N) & N < 2 & supply(_, Max) & Max > 10.
condition("Terran Academy") :- not(friendly(_, "Terran Academy", _, _, _, _, _)) & friendly(_, "Terran Barracks", _,_,_, _, _).
condition("Terran Engineering Bay") :- not(friendly(_, "Terran Engineering Bay", _, _, _, _, _)) & friendly(_, "Terran Barracks", _,_,_, _, _).
condition("Terran Bunker") :- friendly(_, "Terran Barracks", _,_,_, _, _).


canBuild(Building, X, Y) 
	:- 	cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ &
		friendly(_, "Terran Command Center", Id, _, _, _, _) & 
		jia.findBuildingLocation(Id, Building, X, Y) & 
		buildTilePosition(MyX,MyY) & 
		jia.tileDistance(MyX,MyY,X,Y,D) &
		.findall([OtherX,OtherY,OtherD], (friendly(Name, "Terran SCV", _, _, _, OtherX, OtherY) & jia.tileDistance(OtherX,OtherY,X,Y,OtherD) & OtherD < D), []).
	
+!scouting
	:	not(busy) &
		friendly(Name, "Terran Command Center", _, ComX, ComY, _, _ ) &
		position(MyX,MyY) &
		jia.tileDistance(MyX,MyY,ComX,ComY,D) &
		.findall([Name,OtherX,OtherY,OtherD], (friendly(Name, _, _, OtherX, OtherY, _, _)), M) &
		map(MapWidth,MapHeight)& 
		M = [] &
		.random(Rand1)& X = Rand1 * MapWidth &
		.random(Rand2)& Y = Rand2 * MapHeight 
	<-	+busy; move(X,Y); .wait(5000);-busy. 
	
+!scouting  <-.wait(200).
-!scouting  <-.wait(200).

+constructing <- -building(_).

+!constructBase
	:	building(Building) | constructing
	<-	.wait(1000); !constructBase.
+!constructBase
	:	condition(Building) &
		canBuild(Building, X, Y)
	<-	!build(Building, X, Y).
+!constructBase
	:	Building = "Terran Refinery" &
		vespeneGeyser(Id, _, _, X, Y) & 
		friendly(_, "Terran Supply Depot", _, _, _, _, _) 
	<-	!build(Building, X-2, Y-1).
-!constructBase <-.wait(200).
+!constructBase  <-.wait(200).

+!gather
	:	not(busy) &
		friendly(_, "Terran Refinery", Id, _, _, _, _) &
		.findall(_, gathering(_, vespene), L) & .length(L, N) & N < 3
	<-	+busy; gather(Id); .wait(2000); -busy.
+!gather 
	:	not(busy) &
		not(gathering(_)) & 
		.findall(Id,mineralField(Id,_,_,_,_) , L)&
		id(MyId)&
		jia.closest(MyId,L,ClosestId)
	<-	+busy;gather(ClosestId); .wait(2000); -busy.
 
-!gather <- .wait(200).
+!gather  <-.wait(200).

+!build(Building, X, Y)
	:	not(busy) &
		cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ
	<-	+busy; +building(Building); build(Building, X, Y); .wait(2000); +build(Building, X, Y); -busy.
+!build(Building, X, Y)
	<-	.wait(200); !build(Building, X, Y).
