
/*
	Árboles Genéricos
--------------------------------------

Un árbol genérico utiliza dos estrucutras:
	
	a(Etiqueta, [a( ... ),a( ... ),a( ... )])
	
	
*/

:- load_files('crea_ag.pl').
:- load_files('../Listas/creaLista.pl').

/*

	cuentaNodos(+A,-N) es cierto si N unifica con el número de
			   nodos del árbol A.
	
	
*/

cuentaNodos(a(_,L),R) :- cuentaNodos(L,RL), R is RL + 1.

/*

	cuentaNodos(+L,RL) es cierto

*/

cuentaNodos([],0).
cuentaNodos([C|R],RL) :- cuentaNodos(R,N),  
			 cuentaNodos(C,N2),
			  RL is N + N2. 


%A = a(a,[a(f,[a(g,[])]),a(c,[]),a(b,[a(d,[]),a(e,[])])]), cuentaNodos(A,R).



