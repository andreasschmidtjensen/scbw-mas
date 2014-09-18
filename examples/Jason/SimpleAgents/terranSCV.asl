cost("Terran Supply Depot", 100, 0).
cost("Terran Barracks", 150, 0).
cost("Terran Academy", 150, 0).
cost("Terran Engineering Bay", 125, 0).
cost("Terran Bunker", 100, 0).
cost("Terran Refinery", 100, 0).

condition("Terran Supply Depot") :- supply(C, Max) & Max - C < 3.
condition("Terran Barracks") :- .findall(_, friendly(_, "Terran Barracks", _, _, _), L) & .length(L,N) & N < 2 & supply(_, Max) & Max > 10.
condition("Terran Academy") :- not(friendly(_, "Terran Academy", _, _, _)) & friendly(_, "Terran Barracks", _,_,_).
condition("Terran Engineering Bay") :- not(friendly(_, "Terran Engineering Bay", _, _, _)) & friendly(_, "Terran Barracks", _,_,_).
condition("Terran Bunker") :- friendly(_, "Terran Barracks", _,_,_).

canBuild(Building, X, Y) 
	:- 	condition(Building) &
		cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ &
		friendly(_, "Terran Command Center", Id, _, _) & 
		jia.findBuildingLocation(Id, Building, X, Y) & 
		position(MyX,MyY) & 
		jia.tileDistance(MyX,MyY,X,Y,D) &
		.findall([OtherX,OtherY,OtherD], (friendly(Name, "Terran SCV", _, OtherX, OtherY) & jia.tileDistance(OtherX,OtherY,X,Y,OtherD) & OtherD < D), []).

+gameStart <- !work.
+constructing <- -building(_).

+!work
	:	building(Building) | constructing
	<-	.wait(1000); !!work.
+!work
	:	canBuild(Building, X, Y)
	<-	!build(Building, X, Y); .wait(1000); !!work.
+!work
	:	Building = "Terran Refinery" &
		vespeneGeyser(Id, _, _, X, Y) & 
		friendly(_, "Terran Barracks", _, _, _)
	<-	!build(Building, X, Y); .wait(1000); !!work.
+!work
	:	friendly(_, "Terran Refinery", Id, _, _) &
		.findall(_, gathering(_, vespene), L) & .length(L, N) & N < 3
	<-	gather(Id); .wait(1000); !!work.
+!work 
	:	not(gathering(_)) & 
		mineralField(Id, _, _)
	<-	gather(Id); .wait(1000); !!work.
+!work <- .wait(200); !work.
-!work <- .wait(200); !work.

+!build(Building, X, Y)
	:	cost(Building, M, G) & 
		minerals(MQ) & M <= MQ &
		gas(GQ) & G <= GQ
	<-	+building(Building); build(Building, X, Y).
+!build(Building, X, Y)
	<-	.wait(200); !build(Building, X, Y).