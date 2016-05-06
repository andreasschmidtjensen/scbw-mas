:-dynamic
friendly/5,
position/2,
enemy/9,
base/4.

enemyBase(X, Y) :- base(X, Y, 'true', _), 
friendly(_, "Terran Command Center", _, X1, Y1), distance(X, Y, X1, Y1, Res), Res > 10.
		
closestEnemy(RX,RY) 
	:-	findall([D,BX,BY], (enemy(_, Id,_,_, 'false' ,_, _, BX, BY), position(MX, MY), distance(X,Y,MX,MY,D)), L),
		sort(L, [[_,RX,RY]|_]).
		
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2). 