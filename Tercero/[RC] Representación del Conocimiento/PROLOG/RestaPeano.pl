
/*
	Aritmetica de Peano.
	--------------------
	0 - 0.
	1 - s(0).
	2 - s(s(0)).
	
=================================
	Resta de Peano.
	--------------
	resta(x,y,z) es cierto si y solo si z = x - y.
=================================

	Principio de Inducción
	----------------------
	1. P(n0).
	2. Para todo n > 0, P(N) :- N2 is N-1, P(N2).
	
	
*/

resta(X, 0, X).
resta(X, s(Y), R) :- resta(X, Y, s(R)).














