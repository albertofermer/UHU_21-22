
/*
	Aritmetica de Peano.
	--------------------
	0 - 0.
	1 - s(0).
	2 - s(s(0)).
	
	s(x,r) es cierto si r = x + 1.
=================================
	Resta de Peano.
	--------------
	mult(x,y,z) es cierto si y solo si z = x * y.
=================================

	Principio de Inducción
	----------------------
	1. P(n0).
	2. Para todo n > 0, P(N) :- N2 is N-1, P(N2).
	
	
*/

mult(_, 0, 0). /*Variable anónima porque solo aparece una vez en la línea.*/
mult(X, s(Y), R2) :- mult(X, Y, R), suma(R,X,R2).