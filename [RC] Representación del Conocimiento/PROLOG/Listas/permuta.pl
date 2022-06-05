
/*
	insertar_posiciones(Elem, L, R) es cierto si R unifica con una lista con
	los elementos de L con Elem insertado en cualquier posición.
	
	{0} - {1,2,3,4} : {0,1,2,3,4}
			  {1,0,2,3,4}
			  {1,2,0,3,4} ...

*/

	insertar_posiciones(Elem,Lista,[Elem|Lista]).
	insertar_posiciones(Elem, [C|R], [C|I]) :- insertar_posiciones(Elem,R,I).

/*

	permuta(+L,-R) es cierto si R unifica con una lista que tenga los
	elementos de L en otro orden distinto.
	
	
	{1,2,3} - {3,2,1}, {1,3,2}, {2,3,1}, {3,1,2}, {2,1,3}
*/

permuta([],[]).
permuta([Cab|Rest], R2) :- permuta(Rest,R), insertar_posiciones(Cab,R,R2).





