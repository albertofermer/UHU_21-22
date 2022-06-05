
/*
	burbuja(L,R) es cierto si R contiene los elementos de L ordenados.
	{9,7,1} = {1,7,9}
	{7,9,1} - {7,1,9} - {1,7,9} - {1,7,9}
	
*/

burbuja(X,X) :- ordenada(X).
%burbuja([],[]).
%burbuja(E,E) :- num_elem(E,N), N =< 1.
burbuja(Lista,B) :- %num_elem(Lista,N), N > 1,
			append(Ini, [N1,N2|Fin],Lista), 	% Uso reversible del append.
			N1 > N2,			% Si un par de elementos está desordenado.
			append(Ini, [N2,N1|Fin], R),	% Lo ordeno y lo dejo en R.
			burbuja(R,B).			% R está más ordenada que Lista.

/*
	ordenada(Lista) es cierto si Lista unifica con una lista con sus
	elementos ordenados de menor a mayor.

*/

ordenada([]).
ordenada([_]).
ordenada([C1,C2|Resto]) :- C1 =< C2, ordenada([C2|Resto]).