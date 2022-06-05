
/*

	insertionsort(+L,-R) es cierto si R unifica con una lista que contiene
	los mismos elementos que L ordenados de menor a mayor.
	
	+: Variable tiene que estar instanciada para que funcione.
	-: Variable tiene que estar libre.
	?: Variable puede estar o no instanciada.
	
	{7,9,1} - {9,1,7} - {1,7,9}
	
	{7,9,1} -- {1,9}
	
	
*/
insertionsort([],[]).
insertionsort([Cab | Rest],R) :- insertionsort(Rest,R1),
				 insertar_ord(Cab,R1,R).

/*
	insertar_ord(Elem,L,R) es cierto si R unifica con una lista L con el elemento
	Elem insertado de forma ordenada.
	
	{7} -> {1,5,9,11}

*/

insertar_ord(X,[],[X]).
insertar_ord(Elem,[Cab|Rest],[Elem, Cab | Rest]) :- Elem =< Cab.
insertar_ord(Elem,[Cab|Rest],[Cab | R1]) :- Elem > Cab, insertar_ord(Elem,Rest,R1).