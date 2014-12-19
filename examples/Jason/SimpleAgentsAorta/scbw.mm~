ROLES:
gatherer: gather.
builder: build(X).
scout: scouting; spot(X).
armyTrainer: train(Unit, Amount).
attacker: charge; defend.
commander: trainArmy; train("Terran SCV", Amount).
healer: heal.

OBJECTIVES:
scouting.
spot("Vespene Geyser").
spot("Enemy Base").
gather.
trainArmy:
	train("Terran SCV", X);
	train("Terran Marine",X);
	train("Terran Firebat",X);
	train("Terran Medic",X).
train("Terran SCV", X).
train("Terran Marine",X).
train("Terran Firebat",X).
train("Terran Medic",X).
build("Terran Refinery").
build("Terran Barracks").
build("Terran Supply Depot").
build("Terran Academy").
charge.
defend.
heal.

DEPENDENCIES:
attacker > commander: trainArmy.
armyTrainer > builder: build("Terran Refinery").
commander > armyTrainer: train("Terran Firebat",1).
commander > armyTrainer: train("Terran Medic",1).
commander > armyTrainer: train("Terran Marine",1).
builder > scout: spot("Vespene Geyser").
attacker > scout: spot("Enemy Base").

OBLIGATIONS:
gatherer: gather < false | true.
builder: build("Terran Refinery") < false | spot("Vespene Geyser").
builder: build("Terran Barracks") < false | build("Terran Supply Depot").
builder: build("Terran Supply Depot") < false | true.
builder: build("Terran Academy") < false | build("Terran Barracks").
scout: scouting < false | true.
scout: spot("Vespene Geyser") < false | true.
scout: spot("Enemy Base") < false | true.
scout: train("Terran SCV") < false | true.
commander: train("Terran SCV", 5) < false | true.
commander: trainArmy < false | true.
armyTrainer: train("Terran Marine", 1) < false | true.
armyTrainer: train("Terran Medic", 1) < false | build("Terran Refinery").
armyTrainer: train("Terran Firebat", 1) < false | build("Terran Refinery").
attacker: charge < false | spot("Enemy Base"),trainArmy.
attacker: defend < false | true.
healer: heal < false | true.

