
/*
Implementación de la serie de Fibonacci en Prolog.
Fibonacci(N,R) es cierto si R es la secuencia correspondiente a N.

f(0) = 0
f(1) = 1
f(2) = 1
f(3) = 2
f(4) = 3

*/

fibonacci(0,0).
fibonacci(1,1).

fibonacci(N,R) :- N > 1, N2 is N - 1,
			N3 is N - 2,
			fibonacci(N2,R2),
			fibonacci(N3,R3),
			R is R2 + R3.