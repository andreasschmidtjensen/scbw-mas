ROLES:
gatherer: gather.
builder: build(X).
scout: scouting.
armyTrainer: train(Unit, Amount).
attacker: charge; defend.
commander: trainArmy.
healer: heal.

OBJECTIVES:
scouting.
spot("Vespene Geyser").
spot("Enemy Base").
gather.
trainArmy:
	build("Terran Barracks");
	train("Terran SCV", 5);
	train("Terran Marine",1);
	train("Terran Firebat",1);
	train("Terran Medic",1).
train("Terran SCV", 5).
train("Terran Marine",1).
train("Terran Firebat",1):
	build("Terran Academy");
	build("Terran Refinery").
train("Terran Medic",1):
	build("Terran Academy");
	build("Terran Refinery").
build("Terran Refinery"):
	spot("Vespene Geyser").
build("Terran Barracks").
build("Terran Supply Depot").
build("Terran Academy").
charge:
	trainArmy;
	spot("Enemy Base").
defend.
heal.

DEPENDENCIES:
attacker > commander: trainArmy.
attacker > scout: spot("Enemy Base").
builder > scout: spot("Vespene Geyser").
armyTrainer > builder: build("Terran Refinery").
armyTrainer > builder: build("Terran Academy").
commander > builder: build("Terran Barracks").
commander > armyTrainer: train("Terran Firebat",1).
commander > armyTrainer: train("Terran Medic",1).
commander > armyTrainer: train("Terran Marine",1).
commander > armyTrainer: train("Terran SCV",5).

OBLIGATIONS:
gatherer: gather < false | true.
builder: build("Terran Supply Depot") < false | true.
scout: scouting < false | true.
commander: trainArmy < false | true.
attacker: defend < false | true.
attacker: charge < false | true.
healer: heal < false | true.


