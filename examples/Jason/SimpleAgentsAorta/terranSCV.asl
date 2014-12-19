{ include("generalKnowledge.asl") } 
cost("Terran Supply Depot", 100, 0).
cost("Terran Barracks", 700, 0).
cost("Terran Academy", 150, 0).
cost("Terran Engineering Bay", 125, 0).
cost("Terran Bunker", 100, 0).
cost("Terran Refinery", 100, 0).

findBuildingLocation(Id,Building,ResX,ResY) 
	:-	friendly(_, "Terran Command Center", _, _, _, CX, CY) &
		.findall([Dist,X,Y],(constructionSite(X,Y)&distance(CX,CY,X,Y,Dist)), L) &
		.min(L, [_,ResX,ResY]).
	
canBuild(Building, X, Y) 
	:- 	cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ &
		friendly(_, "Terran Command Center", Id, _, _, _, _) & 
		findBuildingLocation(Id, Building, X, Y) & 
		buildTilePosition(MyX,MyY) & 
		distance(MyX,MyY,X,Y,D) &
		.findall([OtherX,OtherY,OtherD], (friendly(Name, "Terran SCV", _, _, _, OtherX, OtherY) & distance(OtherX,OtherY,X,Y,OtherD) & OtherD < D), []).

closest("mineral Field", ClosestId)
	:-	buildTilePosition(MyX,MyY) &
		.findall([Dist,Id],(mineralField(Id,_,_,X,Y)&distance(MyX,MyY,X,Y,Dist)),L) &
		.min(L,[ClosestDist,ClosestId]).
	
+!build(Building)
	:	unit(Building,_)
	<-	+build(Building).
+!build(Building)
	:	Building = "Terran Refinery" &
		vespeneGeyser(Id, _, _, X, Y)&
		buildTilePosition(MyX,MyY) & 
		distance(MyX,MyY,X,Y,D) &
		.findall([OtherX,OtherY,OtherD], (friendly(Name, "Terran SCV", _, _, _, OtherX, OtherY) & distance(OtherX,OtherY,X,Y,OtherD) & OtherD < D), [])
	<-	!build(Building, X-2, Y-1).
+!build(Building)
	:	not Building="Terran Refinery" &
		canBuild(Building, X, Y)
	<-	!build(Building, X, Y).
+!build(Building) <-.wait(200).
-!build(Building) <-.wait(200).

+!build(Building, X, Y)
	:	not(busy) &
		cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ
	<-	+busy; build(Building, X, Y); .wait(2000); -busy.
+!build(Building, X, Y)
	<-	.wait(200); !build(Building, X, Y).
	
+!gather
	:	gathering(vespene) &
		.findall(_, gathering(_, vespene), L) &
		.length(L, N)  &
		.print("Stopping gathering", N) & 
		N >= 3 //Off by one due to own ID not present in gathering(Id,vespene) but in gathering(vespene)
	<-	stop.
+!gather
	:	not(busy) &
		friendly(_, "Terran Refinery", Id, _, _, _, _) &
		.findall(_, gathering(_, vespene), L) &
		.length(L, N) &
		N < 3 &
		.print("Vespene count",N)
	<-	+busy; gather(Id); .wait(2000); -busy.
+!gather 
	:	not(busy) &
		not(gathering(_)) & 
		id(MyId)&
		closest("mineral Field",ClosestId)
	<-	+busy;gather(ClosestId); .wait(2000); -busy.
-!gather <- .wait(200).
+!gather  <-.wait(200).

