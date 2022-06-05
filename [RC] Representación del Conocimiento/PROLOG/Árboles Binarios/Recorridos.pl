
/*
	inorden(+A,-L) es cierto si L unifica con una lista que contiene
	todas las etiquetas del árbol A marcando un recorrido en inorden.
	izq - raiz- der
	
*/

inorden(nil,[]).
inorden(a(E,HI,HD), L) :- inorden(HI, LI), inorden(HD,LD), append(LI,[E|LD],L).

/*

	preorden(+A,-L) es cierto si L unifica con una lista que contiene
	todas las etiquetas del árbol A marcando un recorrido en preorden.
	
	raiz - izq - der

*/

preorden(nil,[]).
preorden(a(E,HI,HD), L) :- preorden(HI,LI), preorden(HD,LD), append([E|LI],LD,L).

/*

	postorden(+A,-L) es cierto si L unifica con una lista que contiene
	todas las etiquetas del árbol A marcando un recorrido en postorden.
	izq - der - raiz
*/

postorden(nil,[]).
postorden(a(E,HI,HD), L) :- postorden(HI,LI), postorden(HD,LD), append([LI,LD,[E]],L).

/*

	anchura(+A,-L) es cierto si L unifica con una lista que contiene
	todas las etiquetas del árbol A marcando un recorrido en anchura.

*/

%anchura(nil,[]).
%anchura(a(E,HI,HD), L) :- anchura(HI,LI), anchura(HD,LD), append([E],LI,R), append(R,LD,L).
