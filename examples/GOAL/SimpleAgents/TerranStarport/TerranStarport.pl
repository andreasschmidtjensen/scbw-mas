:-dynamic
chokepoint/2,
commandCenter/2,
trainVessel/0,
friendly/5,
constructionSite/2.

rallyLocation(X,Y, RX,RY) 
	:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
		sort(L, [[_,RX,RY]|_]). 				
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	
		
buildLocation(X, Y) :- 
		friendly(_, "Terran Command Center", _, TX, TY),
		buildingLocation(TX, TY, X, Y).
				
buildingLocation(X,Y, RX,RY) 
	:-	findall([D,BX,BY], (constructionSite(BX,BY), distance(X,Y,BX,BY,D)), L),
		sort(L, [[_,RX,RY]|_]). 	 