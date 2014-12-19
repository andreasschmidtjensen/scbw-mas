isBuilding("Terran Command Center").
isBuilding("Terran Barracks").
isBuilding("Terran Supply Depot").
isBuilding("Terran Academy").
isBuilding("Terran Engineering Bay").
isBuilding("Terran Refinery").
isBuilding("Terran Machine Shop").
isBuilding("Terran Bunker").
isBuilding("Terran Armory").
isBuilding("Terran Nuclear Silo").
isBuilding("Terran Missile Turret").
isBuilding("Terran Comsat Station").
isBuilding("Terran Factory").

distance(MyX,MyY,X,Y,D)
	:-	D = math.sqrt((MyX-X)**2 + (MyY-Y)**2).
