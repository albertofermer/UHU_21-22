
/*

	mi_maplist(Objetivo,Lista). 
	es cierto si, y solo si el objetivo puede ser aplicado a todos los elementos de Lista.
	Si falla en cualquier elemento de la lista, mi_maplist también lo hará.

	Utilizaremos el predicado call/2

*/

	mi_maplist(_,[]).
	mi_maplist(Obj, [Cab|Rest]) :- call(Obj,Cab), mi_maplist(Obj,Rest).
