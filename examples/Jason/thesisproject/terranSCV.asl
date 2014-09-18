distance(X, Y, XX, YY, D) :- D = (X - XX) + (Y - YY).

cost("Terran Supply Depot", 100, 0).
cost("Terran Barracks", 150, 0).
cost(_, _, _) :- false. 

price(gatherM, 1).
price(buildOne(B), 3).
price(_, _) :- false.

counter(0).
+!incCounter(X): counter(C) <- X = C + 1; -+counter(X).

+cnp(Id, buildOne(B))[source(AN)] : not constructing & position(X, Y) & id(MyId) &
                                    jia.findBuildingLocation(MyId, B, XX, YY) & jia.tileDistance(X, Y, XX, YY, D) 
                                    <- +offer(Id, AN, buildOne(B), D); .print(D); .my_name(Me); .send(AN, tell, propose(Id, D, Me)).
+cnp(Id, gatherM)[source(AN)] : not constructing & price(T, O) <- +offer(Id, AN, T, O); .my_name(Me); .send(AN, tell, propose(Id, O, Me)).
+cnp(Id, T)[source(AN)] <- .print("refused ", T); .my_name(Me); .send(AN, tell, refuse(Id, Me)).

+accept(Id)[source(AN)] : offer(Id, AN, T, _) <- !execute(T).
+accept(Id)[source(AN)] <- .print("cannot accept, refused cpf: ", Id).
+reject(Id)[source(AN)] <- -offer(Id, AN, _, _).

+!execute(buildOne(B)) : cost(B, M, G) <- !incCounter(X); !request(M, G, 0, buildOne(B), X).
+!execute(gatherM) : .findall(Id, mineralField(Id, _, _), L) & .shuffle(L,S) & .member(X,S) <- gather(X).
+!execute(T) <- .print("fail exe").

+!request(M, G, S, T, X) : .my_name(Me) <- .print("request sent"); +requested(M, G, S, T, X); .send("player", tell, request(Me, M, G, S,X)).
+grant(M, G, S, X) : requested(M, G, S, T, X) <- .print("grant received"); -requested(M, G, S, T, X); +granted(T, X).
+grant(M, G, S, X) <- .print("no request made for these resources!").

+granted(buildOne(B), _) : id(MyId) & jia.findBuildingLocation(MyId, B, X, Y) <- build(B, X, Y).
-granted(_, _) <- .print("granted failed?!").