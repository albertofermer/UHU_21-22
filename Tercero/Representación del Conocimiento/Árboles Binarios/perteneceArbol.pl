
/*

	miembro(+N,-A) es cierto si N se encuentra en el árbol A.

*/

miembro(N, a(N,_,_)).

miembro(N, a(N1,HI,_)) :- N < N1, miembro(N,HI).
miembro(N, a(N1,_,HD)) :- N > N1, miembro(N,HD).