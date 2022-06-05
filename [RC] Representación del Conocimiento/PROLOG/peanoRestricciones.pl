/*

	d2p(D,P) es cierto si P corresponde con el valor D en
	peano.

*/


:- use_module(library(clpfd)).


		% Solución para el caso base.
d2p(0,0).

/*
	Para todo D mayor que 0, P(N) :- N2 is N-1, P(N2).
*/
		% Solución para el caso general.
		
d2p(D,s(P)) :- D #> 0, D2 #= D - 1, d2p(D2,P).
