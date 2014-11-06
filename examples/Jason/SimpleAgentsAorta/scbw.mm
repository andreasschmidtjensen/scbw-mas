ROLES:
gatherer: gather.
builder: build.
scout: scouting.

OBJECTIVES:
work.
scouting.

OBLIGATIONS:
gatherer: gather < false | true.
builder: build(X) < false | condition(X).
scout: scouting  < false | me(Me).

RULES:
condition(X):-true.
d(d) :- true.

