
/***
=================================
	GRAFOS
=================================


Para representar un grafo usaremos una
estructura grafo compuesta de una lista 
de nodos y una lista de aristas.

grafo([b,c,d,f,g,h,k],[a(b,c), a(b,f), a(c,f), a(f,k), a(g,h)]).

***/

/**

Ejercicio: camino(+Inicio, +Fin, +Grafo, +Visitados, -Camino, <-Peso>, <-Camino2>)
	     será cierto cuando -Camino unifique con una lista de vértices o
	     aristas de un grafo que empiece en +Inicio y acabe en +Fin sin repetir
	     los vértices o aristas de la lista de visitados.

**/

camino(I,I,_,_,[]).
%camino(Inicio,Fin,g(V,A),_, [a(Inicio,Fin)] ) :- conectado(Inicio,Fin,A).

camino(Inicio, Fin, g(_,A), Visitados, [Inicio| C] ) :- 
				  conectado(Inicio,I2,A),
				  \+ member(a(Inicio,I2), Visitados),
				  camino(I2,Fin,g(_,A),[a(Inicio,I2),a(I2,Inicio)|Visitados], C).


/**

	conectado(+I,+I2,+A) es cierto si existe una conexión directa entre I
				e I2 en la lista de Aristas A.

**/
conectado(I,I2,A) :- member(a(I,I2),A).
conectado(I,I2,A) :- member(a(I2,I),A). % Se añade para los grafos no dirigidos.

grafo([b,c,d,f,g,h,k],[a(b,c), a(b,f), a(c,f), a(f,k), a(g,h)]).
grafo1([a,b,c,d,e],[a(a,b), a(a,c), a(b,c), a(b,d), a(b,e),a(c,d), a(c,e), a(d,e)]).