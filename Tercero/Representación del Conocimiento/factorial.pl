% Comentarios de una línea. Mi primer programa de Prolog

/*

	Comentarios en un bloque.
	Primer Ejemplo en Prolog.

Factorial(N,R).
	es cierto si R es el factorial de N.

*/

factorial(1, 1).

factorial(N, R2) :- N > 1, N2 is N - 1,
			factorial(N2,R),
			R2 is N * R.




