:- dynamic trainMarine/0,
trainMedic/0,
trainGhost/0,
chokepoint/2,
commandCenter/2,
rallyLocation/4.


rallyLocation(X,Y, RX,RY) 
	:-	findall([D,BX,BY], (chokepoint(BX,BY), distance(X,Y,BX,BY,D)), L),
		sort(L, [[_,RX,RY]|_]).
		 				
distance(X1,Y1,X2,Y2,D) :- D is sqrt((X2-X1)**2 + (Y2-Y1)**2).	 