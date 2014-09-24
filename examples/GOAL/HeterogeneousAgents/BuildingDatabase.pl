		isBuilding('Terran Command Center').
		isBuilding('Terran Barracks').
		isBuilding('Terran Supply Depot').
		isBuilding('Terran Academy').
		isBuilding('Terran Engineering Bay').
		isBuilding('Terran Refinery').
		isBuilding('Terran Armory').
		
		%buildings
		cost('Terran Supply Depot',100,0,0).
		cost('Terran Barracks',150,0,0).
		cost('Terran Engineering Bay',125,0,0).
		cost('Terran Academy',150,0,0).
		cost('Terran Command Center',400,0,0).
		cost('Terran Refinery',100,0,0).
		cost('Terran Bunker',100,0,0).
		cost('Terran Factory',200,100,0).
		cost('Terran Missile Turret',75,0,0).
		cost('Terran Starport',150,100,0).
		cost('Terran Armory',100,50,0).
		
		
		shouldConstruct('Terran Supply Depot'):-percept(supply(S,TotalS)),CompareS is TotalS-14,S>=CompareS,TotalS<400.
				
		shouldConstruct('Terran Barracks'):- percept(supply(S,TotalS)), TotalS > 20,
							percept(unit('Terran Barracks', Count)),
							percept(minerals(M)),
							shouldConstruct('Terran Barracks',Count,M).
									
		shouldConstruct('Terran Barracks'):- percept(supply(S,TotalS)), TotalS > 20,
							not(percept(unit('Terran Barracks', _))),
							percept(minerals(M)),
							shouldConstruct('Terran Barracks',0,M).
									
		shouldConstruct('Terran Barracks',0,M):-M>=150.
		shouldConstruct('Terran Barracks',1,M):-M>=1000.
		shouldConstruct('Terran Barracks',2,M):-M>=2000.
		
		
		shouldConstruct('Terran Refinery') :- percept(vespeneGeyser(_,_,_,_,_)).
									
									
		
		shouldConstruct('Terran Engineering Bay'):-percept(unit('Terran Barracks', _)), 
							not(percept(unit('Terran Engineering Bay', _))).
		
		shouldConstruct('Terran Academy'):-	percept(unit('Terran Barracks', _)), 
							not(percept(unit('Terran Academy', _))).
			
													
		
		%For some reason the position received from EISBW is off 
		%since we receive the center building block and the bottom 
		%left one is needed.
		someConstructionSite('Terran Refinery',X1,Y1):- 
				findall( (BestX,BestY),percept(vespeneGeyser(_,_,_,BestX,BestY)), [C1|Rest]),
				percept(friendly(_, 'Terran Command Center', _,_,_,CX,CY)),
				someConstructionSite(C1, Rest, CX, CY,[], (X,Y)),X1 is X - 2 , Y1 is Y-1.
				
		someConstructionSite(_,X,Y):- 
				findall( (BestX,BestY),percept(constructionSite(BestX,BestY)), [C1|Rest]),
				findall( (BuildX,BuildY),(percept(friendly(_,Type,_,_,_,BuildX,BuildY)), isBuilding(Type)), BuildingList),
				percept(friendly(_, 'Terran Command Center', _,_,_,CX,CY)),
				someConstructionSite(C1, Rest, CX,CY,BuildingList, (X,Y)).
									
		someConstructionSite(ConstructionSite, [], CX,CY,BuildingList, ConstructionSite).
		someConstructionSite((BestX,BestY), [(X,Y)|Rest], CX,CY,BuildingList, ConstructionSite) :-
		  		distance(BestX,BestY,CX,CY,Res),
		  		distance(X,Y,CX,CY,Res1), 
		  		Res >Res1 , distanceLargerThan(BuildingList, X,Y) -> someConstructionSite((X,Y), Rest, CX,CY,BuildingList, ConstructionSite)
		  				;someConstructionSite((BestX,BestY), Rest, CX,CY,BuildingList, ConstructionSite).
		
		distanceLargerThan([(CX,CY)|Rest],X,Y) :-distance(X,Y,CX,CY,Res),
					  		    Res > 4 -> distanceLargerThan(Rest,X,Y)
					  				;!,fail.
		
		chooseWorker(Type,BestId,ConX,ConY):- 
				findall( (Id,X,Y),(percept(friendly(_, 'Terran SCV',Id,_,_,X,Y)),not(percept(workerActivity(Id,constructing)))), [W1|Rest]),
				someConstructionSite(Type,ConX,ConY),
				chooseWorker(W1, Rest,ConX,ConY, (BestId,_,_)).
									
		chooseWorker(Worker, [],X,Y, Worker).
		chooseWorker((BestId,BestX,BestY), [(Id,X,Y)|Rest],ConX,ConY, Worker) :-
		  		distance(BestX,BestY,ConX,ConY,Res),
		  		distance(X,Y,ConX,ConY,Res1), 
		  		Res <Res1 -> chooseWorker((BestId,BestX,BestY), Rest,ConX,ConY, Worker)
			        	; chooseWorker((Id,X,Y), Rest,ConX,ConY, Worker).
