
/*

	concatenar(L1,L2,L3) es cierto si L3 contiene los elementos de L1
	seguidos de los elementos de L2.
	
	{1,2,3} + {4,5,6} = {1,2,3,4,5,6}
	{2,3} + {4,5,6} = {2,3,4,5,6}
		{1} + {2,3,4,5,6}
*/

concatenar([],X,X).
concatenar([C|R],L2,[C|L]) :- concatenar(R,L2,L).  