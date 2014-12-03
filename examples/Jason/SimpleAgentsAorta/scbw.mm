ROLES:
gatherer: gather.
builder: constructBase.
scout: scouting.
armyTrainer: train("Terran Marine",X);train("Terran Firebat",X);train("Terran Medic",X).
attacker: attack.

OBJECTIVES:
scouting.
constructBase.
gather.
train("Terran Marine",X).
train("Terran Firebat",X).
train("Terran Medic",X).

DEPENDENCIES:
attacker > armyTrainer: train(X,10).

OBLIGATIONS:
gatherer: gather < false | true.
builder: constructBase < false | true.
scout: scouting < false | true.
armyTrainer: train("Terran Marine",10) < false | true.
armyTrainer: train("Terran Firebat",10) < false | true.
armyTrainer: train("Terran Medic",10) < false | true.
attacker: attack < false | train(X,10).


