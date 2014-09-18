weigh(EID, W) :- .count(attacking(_ ,EID), N) & id(ID) & jia.distance(ID, EID, D) & W = w(EID, N, D).

+gameStart <- .random(X); .wait(500 * X); !!attack.

+!attack : id(ID) & attacking(ID, T) 
    & weigh(T, w(T, TN, TD)) 
    & .findall(t(N, D, EID), (enemy(_, EID, _, _) & weigh(EID, w(EID, N, D)) & N + 1 < TN), WS) 
    & .min(WS, t(_, _, EID))
        <- attack(EID); !attack.

+!attack : id(ID) & not attacking(ID, _) 
    & .findall(w(N, D, EID), (enemy(_, EID, _, _) & weigh(EID, w(EID, N, D))), WS) & .min(WS, w(_, _, EID)) 
        <- attack(EID); !attack.

+!attack <- .wait(300); !attack.