/*

	mi_maplist(Objetivo,Lista1,Lista2). 
	es cierto si, y solo si el objetivo puede ser aplicado a todos los elementos de Lista.
	Si falla en cualquier elemento de la lista, mi_maplist también lo hará.

	Utilizaremos el predicado call/3

*/

% 2 argumentos
mi_maplist(_,[]).
mi_maplist(Obj, [Cab|Rest]) :- call(Obj,Cab), mi_maplist(Obj,Rest).

%3 argumentos
mi_maplist(_,[],[]).
mi_maplist(Obj,[Cab1|Rest1],[Cab2|Rest2]) :- call(Obj,Cab1,Cab2), mi_maplist(Obj,Rest1,Rest2).

%4 argumentos
mi_maplist(_,[],[],[]).
mi_maplist(Obj,[Cab1|Rest1],[Cab2|Rest2],[Cab3|R3]) :- call(Obj,Cab1,Cab2,Cab3), mi_maplist(Obj,Rest1,Rest2,R3).

%5 argumentos
mi_maplist(_,[],[],[],[]).
mi_maplist(Obj,[Cab1|Rest1],[Cab2|Rest2],[Cab3|R3],[Cab4|R4]) :- call(Obj,Cab1,Cab2,Cab3,Cab4), mi_maplist(Obj,Rest1,Rest2,R3,R4).