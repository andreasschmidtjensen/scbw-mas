:- dynamic
builder/0,
minerals/1,
gathering/1,
unit/2,
gas/1,
mineralField/3,
supply/2,
position/2,
constructionSite/2,
refinery/3,
workerActivity/2,
isStuck/0,
id/1,
me/1,
chokepoint/2,
base/4,
constructing/0,
friendly/5.

		busy :- constructing ; gathering(_).
		cost("Terran Command Center",400,0).
		cost("Terran Supply Depot", 100, 0).
		cost("Terran Barracks", 150, 0).
		cost("Terran Bunker", 100, 0).
		cost("Terran Refinery", 100, 0).
		cost('Terran Academy',150,0).
		cost("Terran Engineering Bay", 125, 0).
		cost("Terran Factory", 200, 100).
		cost('Terran Starport',150,100).
		cost("Terran Science Facility", 100, 150).
		condition("Terran Academy")
			:- friendly(_, "Terran Barracks", _, _, _).
		condition("Terran Supply Depot") 
			:- supply(C, Max), Max - C < 6.
		condition("Terran Barracks") 
			:- supply(C, Max), Max >= 20, Max - C > 6,
				((unit("Terran Barracks",Num), Num < 3) ; not(unit("Terran Barracks",_))).
		condition("Terran Refinery").
		canBuild(Building, X, Y) 
			:- 	condition(Building), 
				cost(Building, M, G), 
				minerals(MQ), M =< MQ, 
				gas(GQ), G =< GQ,
				friendly(_, "Terran Command Center", _, TX, TY),
				buildingLocation(TX, TY, X, Y),
				position(MyX,MyY), distance(MyX,MyY,X,Y,D),
				findall(_, (friendly(_, "Terran SCV", _, OtherX, OtherY),
							distance(OtherX,OtherY,X,Y,OtherD), D < OtherD), []).
								
		buildingLocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (constructionSite(BX,BY), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 
				
		addonBuildingLocation(RX, RY)
			:-	friendly(_, "Terran Command Center", _, X, Y),
				findall([D,BX,BY], (constructionSite(BX,BY), distance(X,Y,BX,BY,D), (constructionSite(HX, BY), HX is X + 1)), L),
				sort(L, [[_,RX,RY]|_]).
						
		distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).
		
		chokepointlocation(X,Y, RX,RY) 
			:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 	
				
		nearestBase(X, Y, RX, RY) 
				:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
				sort(L, [[_,RX,RY]|_]). 	
				
		shouldMineVespene :- unit('Terran Refinery',RefCount),
								NeededWorkers is RefCount * 2,
								aggregate_all(count, workerActivity(_,gatheringGas), Count),
								Count < NeededWorkers.
								
		buildLocation(X, Y) :- 
				friendly(_, "Terran Command Center", _, TX, TY),
				buildingLocation(TX, TY, X, Y).
		
		commandCenter(X,Y)
			:- friendly(_, "Terran Command Center", _ , X, Y). 