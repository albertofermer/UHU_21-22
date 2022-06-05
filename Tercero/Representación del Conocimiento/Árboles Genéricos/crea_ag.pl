
/*

	crea_ag(+ListaEt,-A) es cierto si A unifica con un árbol
	genérico que contiene los elementos de ListaEt.

*/

crea_ag1([E], a(E,[]) ).
crea_ag1(L,  a(C,[RR])) :-  length(L,LL),
				LL > 1,
				L = [C|R],
				crea_ag1(R, RR).
				
crea_ag2([E],a(E,[])).

crea_ag2([E1,E2],a(E1,[a(E2,[] ) ] ) ).
			
crea_ag2(Lista, a(C,[A1,A2]) ) :- 	
					Lista = [C|Resto],
					length(Lista,LL),
					LL > 2,
					LR is LL - 1,
					Mitad is LR div 2,
					length(L1,Mitad),
					append(L1,L2,Resto),
					crea_ag2(L1, A1),
					crea_ag2(L2, A2).
	

/*

	crea_agN(+L,+N,-A) es cierto si A unifica con un árbol genérico
			   que contiene las etiquetas de L con un máximo de N
			   hijos por nivel.

*/	
%crea_agN(N,).
%crea_agN(N, [C|R], ) :- length(L,N), append(L,LR,R), 
%			maplist(crea_agN()). 					
			
/*

	crea_sub_list(+N,+L,-R) es cierto si R unifica con una lista de listas de 
				tamaño N con los elementos de L.

*/

%crea_sub_list(N, L, [L]) :- length(L,LL), LL =< N. 
%crea_sub_list(N,L,R) :- 