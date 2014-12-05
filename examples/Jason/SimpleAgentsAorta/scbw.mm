ROLES:
gatherer: gather.
builder: buildTerranRefinery;buildTerranBarracks;buildTerranSupplyDepot;buildTerranAcademy.
scout: scouting; spotVespeneGeyser; spotEnemy.
armyTrainer: trainTerranMarineX;trainTerranFirebatX;trainTerranMedicX.
attacker: charge.
commander: trainTerranSCV.
healer: heal.

OBJECTIVES:
scouting.
%spot("Vespene Geyser").
spotVespeneGeyser.
spotEnemy.
constructBase.
gather.
%train("Terran SCV").
%train("Terran Marine",X).
%train("Terran Firebat",X).
%train("Terran Medic",X).
%build("Terran Refinery").
trainTerranSCV.
trainTerranMarineX.
trainTerranFirebatX.
trainTerranMedicX.
buildTerranRefinery.
buildTerranBarracks.
buildTerranSupplyDepot.
buildTerranAcademy.
charge.
heal.

DEPENDENCIES:
%attacker > armyTrainer: train(X,10).
%armyTrainer > builder: build("Terran Refinery").
%builder > scout: spot("Vespene Geyser").
attacker > armyTrainer: trainTerranFirebatX.
attacker > scout: spotEnemy.
healer > armyTrainer: trainTerranFirebatX.
healer > scout: spotEnemy.
armyTrainer > builder: buildTerranRefinery.
builder > scout: spotVespeneGeyser.

OBLIGATIONS:
gatherer: gather < false | true.

%builder: constructBase < false | true.
%builder: build("Terran Refinery") < false | spot("Vespene Geyser").
builder: buildTerranRefinery < false | spotVespeneGeyser.
builder: buildTerranBarracks < false | buildTerranSupplyDepot.
builder: buildTerranSupplyDepot < false | true.
builder: buildTerranAcademy < false | buildTerranBarracks.

scout: scouting < false | true.
%scout: spot("Vespene Geyser") < false | true.
scout: trainTerranSCV < false | true.
scout: spotEnemy < false | true.
scout: spotVespeneGeyser < false | true.

%commander: train("Terran SCV") < false | true.
commander: trainTerranSCV < false | true.

%armyTrainer: train("Terran Marine",10) < false | true.
%armyTrainer: train("Terran Firebat",10) < false | build("Terran Refinery").
%armyTrainer: train("Terran Medic",10) < false | build("Terran Refinery").
armyTrainer: trainTerranMarineX < false | true.
armyTrainer: trainTerranFirebatX < false | buildTerranRefinery.
armyTrainer: trainTerranMedicX < false | buildTerranRefinery.

attacker: charge < false | spotEnemy,trainTerranFirebatX.
healer: heal < false | spotEnemy,trainTerranFirebatX.


