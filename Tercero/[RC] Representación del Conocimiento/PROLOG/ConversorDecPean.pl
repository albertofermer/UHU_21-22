
/*
	Convierte un numero decimal a un numero en Peano.
	d2p(D,P) es cierto si P es el numero D en Peano.
	
	0 - 0
	1 - s(0)
	2 - s(s(0))
	...

*/

d2p(0,0).
d2p(D ,s(P)) :- D > 0, D1 is D - 1, d2p(D1,P).

%	Se puede aplicar el principio de inducción con la segunda variable.
%	Esta forma es reversible porque aplica el is a un valor que 
%	ya está instanciado.

d2p_rev(0,0).
d2p_rev(X2,s(Y)) :- d2p_rev(X1,Y), X2 is X1 + 1.
