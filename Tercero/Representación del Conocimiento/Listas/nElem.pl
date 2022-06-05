
/*

	elemento_enesimo(Pos,Lista,Elem) es cierto cuando Elem unifica con el
	elemento que ocupa la posicion Pos dentro de la Lista.
	
	3 , [1,4,5,0]	- 5
	2 , [4,5,0]	- 5
	


*/
elemento_enesimo(0,[C|_],C).

elemento_enesimo(Pos,[_|R2],E) :- Pos > 0, Pos2 is Pos - 1,
				elemento_enesimo(Pos2,R2,E).
				
elemento_enesimo(Pos,_,E) :- Pos < 0, E = "Índice negativo.".

