buildTilePosition(X,Y) :- percept(buildTilePosition(X,Y)).
position(X,Y) :- percept(position(X,Y)).

walkDistanceTo(X1,Y1,Res):-position(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distanceTo(X1,Y1,Res):-buildTilePosition(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2).