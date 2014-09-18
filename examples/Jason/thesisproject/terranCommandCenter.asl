hasAllResponded(Id) :- contractsSent(Id, C) & .count(propose(Id, _, _), Ps) & .count(refuse(Id, _), Rs) & C = Ps + Rs.

counter(0).

+!incCounter(X): counter(C) <- X = C + 1; -+counter(X).

+gameStart <- .wait(200); !!buildSCVs; !!keepWorkersBusy.

+!startCNP(Id, gatherM, []) <- +cnpState(Id, all, propose); .findall(AN, friendly(AN, "Terran SCV", _, _, _), ANS); !sendContracts(Id, gatherM, ANS).
+!startCNP(Id, gatherM, ANS) <- .print("cnp got list of agents"); +cnpState(Id, all, propose); !sendContracts(Id, gatherM, ANS).
+!startCNP(Id, buildOne(B)) <- +cnpState(Id, single, propose); .findall(AN, friendly(AN, "Terran SCV", _, _, _), ANS); !sendContracts(Id, buildOne(B), ANS).

+!sendContracts(Id, T, ANS) <- .length(ANS, C); +contractsSent(Id, C); .send(ANS, tell, cnp(Id, T)).

+propose(Id, O, _) : cnpState(Id, _, propose) & hasAllResponded(Id) <- .print("finalize begin"); !finalize(Id).
+refuse(Id, _) : cnpState(Id, _, propose) & hasAllResponded(Id) <- .print("finalize begin"); !finalize(Id).


+supply(N,M) : (M - 6) <= N & 400 > M  <- !incCounter(X); !startCNP(X, buildOne("Terran Supply Depot")).
+supply(N,M) : N > 24 & not friendly(_, "Terran Barracks", _, _, _) <- !incCounter(X); !startCNP(X, buildOne("Terran Barracks")).


@fs[atomic]
+!finalize(Id) : cnpState(Id, single, propose) <- +cnpState(Id, single, contracting);
                .findall(offer(O,AN), propose(Id, O, AN), Os);
                Os \== [];
                .min(Os, offer(WO, WAN));
                !announceResult(Id, Os, WAN);
                +cnpState(Id, single, done);
                -propose(Id, _, _);
                -refuse(Id, _, _).

@fa[atomic]
+!finalize(Id) : cnpState(Id, all, propose) <- +cnpState(Id, all, contracting);
                .findall(AN, propose(Id, _, AN), ANS);
                ANS \== [];
                !announceWinners(Id, ANS);
                +cnpState(Id, all, done);
                -propose(Id, _, _);
                -refuse(Id, _, _).

@ff[atomic]
-!finalize(Id) <- .print("finalize failed, no offers").

@f +!finalize(Id).

+!announceWinners(Id, ANS) <- .send(ANS, tell, accept(Id)).

+!announceResult(_, [], _).
+!announceResult(Id, [offer(O,WAN)|T], WAN) <- .send(WAN, tell, accept(Id)); !announceResult(Id, T, WAN).
+!announceResult(Id, [offer(O, LAN)|T], WAN) <- .send(LAN, tell, reject(Id)); !announceResult(Id, T, WAN).

/* Build SCVs until there are enough of them */

+!buildSCVs : queue(0) & .count(friendly(_, "Terran SCV", _, _, _), C) & C < 24 & not requested(_, _, _, buildSCV, _) & counter(X) <- !incCounter(_); !request(50, 0, 2, buildSCV, X); .wait(500); !buildSCVs.
+!buildSCVs : queue(N) & N > 0 <- .wait(500); !buildSCVs.
+!buildSCVs : requested(_, _, _, buildSCV, _) <- .wait(2000); !buildSCVs.
+!buildSCVs.

+!request(M, G, S, T, X) : .my_name(Me) <- .print("request sent"); +requested(M, G, S, T, X); .send("player", tell, request(Me, M, G, S, X)).
+grant(M, G, S, X) : requested(M, G, S, T, X) <- .print("grant received"); -requested(M, G, S, T, X); !execute(T).
+grant(M, G, S, X) <- .print("no request made for these resources!").

+!execute(buildSCV) <- train("Terran SCV").

+!keepWorkersBusy : .findall(AN, idleWorker(AN), ANS) & ANS \== [] <- !incCounter(X); !startCNP(X, gatherM, ANS); .wait(2000); !keepWorkersBusy.
+!keepWorkersBusy <- .wait(2000); !keepWorkersBusy.
