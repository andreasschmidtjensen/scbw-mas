ROLES:
gatherer: gather.
builder: build(X).
scout: scouting; spot(X).
armyTrainer: train(Unit, Amount).
attacker: charge; defend.
commander: train("Terran SCV", Amount).
healer: heal.

OBJECTIVES:
scouting.
spot("Vespene Geyser").
spot("Enemy Base").
gather.
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
attacker > armyTrainer: train("Terran Firebat",1).
attacker > armyTrainer: train("Terran Medic",1).
attacker > armyTrainer: train("Terran Marine",1).
armyTrainer > builder: build("Terran Refinery").
builder > scout: spot("Vespene Geyser").
attacker > scout: spot("Enemy Base").
healer > armyTrainer: train("Terran Firebat", 1).
healer > armyTrainer: train("Terran Medic", 1).
healer > armyTrainer: train("Terran Marine", 1).
healer > scout: spot("Enemy Base").

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
armyTrainer: train("Terran Marine", 1) < false | true.
armyTrainer: train("Terran Medic", 1) < false | build("Terran Refinery").
armyTrainer: train("Terran Firebat", 1) < false | build("Terran Refinery").
attacker: charge < false | spot("Enemy Base"),train("Terran Marine", 1),train("Terran Firebat", 1),train("Terran Medic", 1).
attacker: defend < false | true.
healer: heal < false | true.

