{ include("generalKnowledge.asl") } 
+gameStart <- !!matchUp; !!heal.
+!matchUp
	:	Type = "Terran Firebat" &
		friendly(_, Type, Id, _, _, _, _)
	<-	+match(Id); .print("Matched with: ", Id).

+!matchUp
	<-	.wait(1000); !matchUp.
	
+!heal 
	:	match(Id) &
		friendly(_, _, Id, WX, WY, _, _) & 
		position(MyX,MyY) &
		distance(WX,WY,MyX,MyY,Distance) &
		Distance > 16
	<-	move(WX,WY).
+!heal
	:	match(Id) &
		not friendly(_, _, Id, _, _, _, _)
	<-	-match(Id); !matchUp.
+!heal
	:	not match(Id)
	<-	!matchUp.
-!heal 
	<-	.wait(200); !!heal.
