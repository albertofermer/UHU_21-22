/*

	dividir(+Elem,+L,-Men,-May) es cierto cuando Men unifica con una lista que
	contenga los elementos de L menores que Elem y May unifica con una lista que
	contenga los elementos de L mayores que ELem.
	{5,8,9,1,4,7} - Elem = 5.
	{5,1,4} {8,9,7}
	
*/

dividir(_,[],[],[]).
dividir(Elem,[Cab | Rest],[Cab|Men],May) :- 	Cab =< Elem,
						dividir(Elem,Rest,Men,May).
						
dividir(Elem,[Cab | Rest],Men,[Cab|May]) :- 	Cab > Elem,
						dividir(Elem,Rest,Men,May).
						
/*

	quicksort(+L,-R) es cierto cuando R unifica con una lista con los elementos 
	de L ordenados de menor a mayor.

*/
quicksort([],[]).
quicksort([Cab|Rest],R) :- 	dividir(Cab, Rest,Men,May),
				quicksort(Men,R2),
				quicksort(May,R1),
				append(R2,[Cab | R1],R).


					    