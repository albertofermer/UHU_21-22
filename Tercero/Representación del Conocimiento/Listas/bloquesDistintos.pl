% bloques_distintos(Lista1, Lista2, Lista3)
% es cierto si los bloques 3x3 que forman las tres listas
% tienen valores diferentes. Vease el siguiente ejemplo.
% |1,2,3|4,5,6|7,8,9|
% |4,5,6|7,8,9|1,2,3|
% |7,8,9|1,2,3|4,5,6|

% |1,2,3|1,2,3|1,2,3|
% |4,5,6|4,5,6|4,5,6|
% |7,8,9|7,8,9|7,8,9|

bloques_distintos([],[],[]).				
bloques_distintos([A1,A2,A3 | R1], [B1,B2,B3 |R2], [C1,C2,C3 | R3]) :- todos_distintos([A1,A2,A3,B1,B2,B3,C1,C2,C3]),
									bloques_distintos(R1,R2,R3).
/*

	todos_distintos(L) es verdadero cuando todos los elementos de L son distintos entre sí.

*/
	todos_distintos([]).
	todos_distintos([_]).
	todos_distintos(L) :-  msort(L,[C1,C2| R]),
					C1 \= C2, 
					todos_distintos([C2|R]).


