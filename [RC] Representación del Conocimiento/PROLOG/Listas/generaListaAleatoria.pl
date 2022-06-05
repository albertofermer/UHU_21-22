
/*

	crea_lista_random(+Tama,-L) es cierto si L unifica con una lista 
	de tamaño Tama de elementos aleatorios.
*/

	crea_lista_random(0,[]).
	crea_lista_random(T,[C|R]) :- T > 0, T1 is T - 1, crea_lista_random(T1, R), random(0,T,C). 