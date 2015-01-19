canAfford(Type) :- cost(Type, CostM, CostG, CostS),
		minerals(M), 
		gas(G), 
		supply(S,TS), 
		DiffS is TS - S, 
		CostM =< M, 
		CostG =< G, 
		CostS =< DiffS.