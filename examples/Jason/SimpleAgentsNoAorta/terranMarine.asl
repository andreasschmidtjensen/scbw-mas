{ include("generalKnowledge.asl") } 
{ include("groundUnit.asl") } 
{ include("scouting.asl") }
ignored(builder).

+gameStart <- !!charge; !!defend; !!spot("Vespene Geyser"); !!spot("Enemy Base"); !!scouting;!move; .