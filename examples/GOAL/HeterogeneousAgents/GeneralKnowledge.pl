%id(X) :- percept(id(X)).
%unitType(X) :- percept(unitType(X)). 
%queueSize(X):-percept(queueSize(X)).
%supply(X,Y):- percept(supply(X,Y)).
buildTilePosition(X,Y) :- percept(buildTilePosition(X,Y)).
position(X,Y) :- percept(position(X,Y)).
%isBeingConstructed :- percept(isBeingConstructed).
%totalRes(M,CM,G,CG,S,TS) :- percept(totalRes(M,CM,G,CG,S,TS)).
%friendly(Name,Type,Id,WalkX,WalkY,BuildX,BuildY) :- percept(friendly(Name,Type,Id,WalkX,WalkY,BuildX,BuildY)).
%enemy(Name,Id,WalkX,WalkY,BuildX,BuildY) :- percept(enemy(Name,Id,WalkX,WalkY,BuildX,BuildY)).
%mineralField(Id,M, T, X,Y) :- percept(mineralField(Id,M,T,X,Y)).
%gathering(X) :- percept(gathering(X)).
%constructing :- percept(constructing).
%idle :- percept(idle).
%constructionSite(BestX,BestY) :- percept(constructionSite(BestX,BestY)).
%buildUnit(X) :- percept(buildUnit(X)).
%attacking(Id,OId) :- percept(attacking(Id,OId)).
%minerals(M) :- percept(minerals(M)).
%gas(G) :- percept(gas(G)).

walkDistanceTo(X1,Y1,Res):-position(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distanceTo(X1,Y1,Res):-buildTilePosition(SelfX,SelfY),distance(X1,Y1,SelfX,SelfY,Res).
distance(X1,Y1,X2,Y2,Res):- Res is sqrt((X2-X1)**2 + (Y2-Y1)**2).