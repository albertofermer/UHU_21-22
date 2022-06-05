/*

	Problema de las dos garrafas.
		Dadas dos garrafas de 5L y 3L, llenar la
		garrafa de 5L con 4L.

	Estado:
		estado(L5,L3):
	
	Estado Inicial:
	
		estado(0,0). Einicial.
	
	Prueba de Meta:
	
		estado(4,_). Efinal.
	
	operadores:
	
		llenar(5L) : Llena la garrafa de 5L entera
		llenar(3L) : Llena la garrafa de 3L entera
		
		vaciar(3L) : Vacía la garrafa de 3L
		vaciar(5L) : Vacía la garrafa de 5L
		
		verter(5L,3L) : Añade a la garrafa de 5L el contenido de la de 3L.
		verter(3L,5L) : Añade a la garrafa de 3L el contenido de la de 5L.
	
	mov(Nombre, EstadoAntes, EstadoDespues).
*/


mov(llenarL5, estado(_,L3), estado(5,L3)).
mov(llenarL3, estado(L5,_), estado(L5,3)).

mov(vaciarL5, estado(_,L3), estado(0,L3)).
mov(vaciarL3, estado(L5,_), estado(L5,0)).

mov(verterL5aL3, estado(L5,L3),estado(0,T)) :- 	T is L5 + L3,
						T =< 3.
						
mov(verterL5aL3, estado(L5,L3),estado(R,3)) :- 	T is L5 + L3,
						T > 3, R is T - 3.
						
mov(verterL3aL5, estado(L5,L3),estado(T,0)) :- 	T is L5 + L3,
						T =< 5.

mov(verterL3aL5, estado(L5,L3),estado(5,R)) :- 	T is L5 + L3,
						T > 5, R is T - 5.
						

% Construir Camino a la Solución.
/*

	camino(+eInicial, +eFinal, +Visitados, -Camino) es cierto cuando Camino
	unifica con la lista de movimientos que hay que hacer para llegar al
	eFinal, sin repetir estado.

*/

camino(I,I,_,[]).
camino(Ei, Ef, Visitados, [Mov|C]) :- mov(Mov,Ei,S), 
				\+ member(S,Visitados),
				camino(S,Ef, [S|Visitados], C).







