cost("Terran Supply Depot", 100, 0).
cost("Terran Barracks", 150, 0).
cost("Terran Academy", 150, 0).
cost("Terran Engineering Bay", 125, 0).
cost("Terran Bunker", 100, 0).
cost("Terran Refinery", 100, 0).

distance(MyX,MyY,X,Y,D)
	:-	D = math.sqrt((MyX-X)**2 + (MyY-Y)**2).

findBuildingLocation(Id,Building,ResX,ResY) 
	:-	friendly(_, "Terran Command Center", _, _, _, CX, CY) &
		.findall([Dist,X,Y],(constructionSite(X,Y)&distance(CX,CY,X,Y,Dist)), L) &
		.min(L, [_,ResX,ResY]).
	
closest("mineralField", ClosestId)
	:-	buildTilePosition(MyX,MyY) &
		.findall([Dist,Id],(mineralField(Id,_,_,X,Y)&distance(MyX,MyY,X,Y,Dist)),L) &
		.min(L,[ClosestDist,ClosestId]).
		
canBuild(Building, X, Y) 
	:- 	cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ &
		friendly(_, "Terran Command Center", Id, _, _, _, _) & 
		findBuildingLocation(Id, Building, X, Y) & 
		buildTilePosition(MyX,MyY) & 
		distance(MyX,MyY,X,Y,D) &
		.findall([OtherX,OtherY,OtherD], (friendly(Name, "Terran SCV", _, _, _, OtherX, OtherY) & distance(OtherX,OtherY,X,Y,OtherD) & OtherD < D), []).

+!buildTerranRefinery
	<-	!build("Terran Refinery").
+!buildTerranBarracks
	<-	!build("Terran Barracks").
+!buildTerranSupplyDepot
	<-	!build("Terran Supply Depot").
+!buildTerranAcademy
	<-	!build("Terran Academy").
	
+!build(Type)
	:	Type = "Terran Refinery" &
		unit(Type,_)
	<-	+buildTerranRefinery.
+!build(Type)
	:	Type = "Terran Barracks" &
		unit(Type,_)
	<-	+buildTerranBarracks.
+!build("Terran Supply Depot")
	:	Type = "Terran Supply Depot" &
		unit("Terran Supply Depot",_)
	<-	+buildTerranSupplyDepot.
+!build(Type)
	:	Type = "Terran Academy" &
		unit(Type,_)
	<-	+buildTerranAcademy.
+!build(Type)
	:	Type = "Terran Refinery" &
		vespeneGeyser(Id, _, _, X, Y) &
		.print(Type,Id,X,Y)
	<-	!build(Type, X-2, Y-1).
+!build(Type)
	:	not Type="Terran Refinery" &
		canBuild(Type, X, Y)
	<-	!build(Type, X, Y).
+!build(Type) <-.wait(200).
-!build(Type) <-.wait(200).

+!build(Building, X, Y)
	:	not(busy) &
		cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ
	<-	+busy; +building(Building); build(Building, X, Y); .wait(2000); +build(Building, X, Y); -busy.
+!build(Building, X, Y)
	<-	.wait(200); !build(Building, X, Y).
	
+!scouting
	:	not(busy) &
		friendly(Name, "Terran Command Center", _, ComX, ComY, _, _ ) &
		position(MyX,MyY) &
		distance(MyX,MyY,ComX,ComY,D) &
		.findall([Name,OtherX,OtherY,OtherD], (friendly(Name, _, _, OtherX, OtherY, _, _)), M) &
		map(MapWidth,MapHeight)& 
		M = [] &
		.random(Rand1)& X = Rand1 * MapWidth &
		.random(Rand2)& Y = Rand2 * MapHeight 
	<-	+busy; move(X,Y); .wait(5000);-busy. 
	
+!scouting  <-.wait(200).
-!scouting  <-.wait(200).

+!spotEnemy
	:	enemy(Id,_,_,X,Y) &
		.findall(Name, agent(Name),Recipients)
	<-	+spotEnemy; .send(Recipients, tell, lastSpottedEnemy(Id,X,Y)).
+!spotEnemy <-.wait(200).
-!spotEnemy <-.wait(200).

+!spotVespeneGeyser
	:	vespeneGeyser(_, _, _, _, _)
	<-	+spotVespeneGeyser.
+!spotVespeneGeyser <-.wait(200).
-!spotVespeneGeyser <-.wait(200).

+!gather
	:	not(busy) &
		friendly(_, "Terran Refinery", Id, _, _, _, _) &
		.findall(_, gathering(_, vespene), L) & .length(L, N) & N < 3
	<-	+busy; gather(Id); .wait(2000); -busy.
+!gather 
	:	not(busy) &
		not(gathering(_)) & 
		id(MyId)&
		closest("mineralField",ClosestId)
	<-	+busy;gather(ClosestId); .wait(2000); -busy.
-!gather <- .wait(200).
+!gather  <-.wait(200).

