%supply(S,CS):-percept(supply(S,CS)).
%minerals(CM):-percept(minerals(CM)).
%gas(CG):-percept(gas(CG)).
canAfford(Type) :- cost(Type, CostM, CostG, CostS),
		minerals(M), 
		gas(G), 
		supply(S,TS), 
		DiffS is TS - S, 
		CostM =< M, 
		CostG =< G, 
		CostS =< DiffS.