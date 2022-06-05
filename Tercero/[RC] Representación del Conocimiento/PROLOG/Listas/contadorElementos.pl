/*

	num_elem(Lista,N) es cierto si N unifica con
	el número de elementos de la Lista.

*/

num_elem([],0).
num_elem([_|R],N) :- num_elem(R,N2), 
			N is N2 + 1.