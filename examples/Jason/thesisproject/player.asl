/* Controls access to Mineral and Gas resources */
peek(Z) :- queue(Q) & .reverse(Q, [entry(AN,M,G,S,X)|_]) & Z = entry(AN, M, G, S, X).
queue([]).
spent(0 ,0).

@a[atomic] +!enqueue(entry(AN, M, G, S, X)) : queue(Q) <- -+queue([entry(AN, M, G, S, X)|Q]).
@b[atomic] +!enqueue(_) <- .print("enqueued wrong type!!!!!!!!").

@c[atomic] +!dequeue(Z) : queue(Q) & .reverse(Q, [entry(AN, M, G, S, X)|T]) <- Z = entry(AN, M, G, S, X); -+queue(T).
@d[atomic] +!dequeue(_) <- .print("dequeue failed??!!!!!!").

@e[atomic] +!peek(Z) : queue(Q) & .reverse(Q, [entry(AN,M,G,S,X)|_]) <- Z = entry(AN, M, G, S, X).
@f[atomic] +!peek(_).

@g[atomic]+request(AN, M, G, S, X) <- .print("request recieved"); !enqueue(entry(AN, M, G, S, X)); !checkQueue. 

@h[atomic]+totalRes(A, B, C, D, E, F) <- !checkQueue.

@i[atomic] +!checkQueue : totalRes(_, MT, _, GT, SC, ST) & peek(entry(AN, M, G, S, X)) & 
                spent(MU, GU) & 
                M <= MT - MU & 
                G <= GT - GU & 
                S <= ST - SC 
                <-  !dequeue(_); 
                    -+spent(MU + M, GU + G); 
                    .send(AN, tell, grant(M, G, S, X)); !checkQueue.
@j[atomic] +!checkQueue.
