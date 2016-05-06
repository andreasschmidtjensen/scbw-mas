:-dynamic
friendly/5,
base/4.

enemyBase(X, Y) :- base(X, Y, 'true', _), 
		friendly(_, "Terran Command Center", _, X1, Y1), distance(X, Y, X1, Y1, Res), Res > 10.
		
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2).