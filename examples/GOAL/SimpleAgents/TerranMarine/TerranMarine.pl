:-dynamic
id/1,
friendly/5,
base/4.

enemyBase(X, Y) :- base(X, Y, 'true', _), 
friendly(_, "Terran Command Center", _, X1, Y1), distance(X, Y, X1, Y1, Res), Res > 10.
		
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2).
 	  	
nearestBase(X, Y, RX, RY) 
	:-	findall([D,BX,BY], (base(BX,BY, 'false', _), distance(X,Y,BX,BY,D)), L),
	sort(L, [[_,RX,RY]|_]).  