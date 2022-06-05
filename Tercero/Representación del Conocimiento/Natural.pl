
/*
	Suponemos que un número natural es aquel que es mayor que 0
	y no tiene cifras decimales.
	
	El caso base será que el 1 es natural.
	natural(N) será verdad solo si N es natural.
	
*/

natural(1.0).
natural(N) :- N > 1.0, N2 is N - 1.0, natural(N2). 