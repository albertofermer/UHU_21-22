
/*

	crea_lista(+N,-L) es cierto si L unifica con una lista de valores de 1 hasta N.
*/

%crea_lista(0,[]).
%crea_lista(N,[N|R]) :- N > 0, N1 is N - 1, crea_lista(N1,R).

crea_lista(N,N,[N]).
crea_lista(Ini,Fin, [Ini|L]) :- Ini =< Fin, Ini2 is Ini + 1, crea_lista(Ini2,Fin, L).