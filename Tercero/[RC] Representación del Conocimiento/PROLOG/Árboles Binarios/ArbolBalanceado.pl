
/*

	esBalanceado(A) es cierto si y solo si, el árbol A es un árbol balanceado.
	
	 >> Un árbol balanceado es, dado un árbol A, para todo nodo de ese árbol, la diferencia entre
	la altura del subárbol derecho y el subárbol derecho es como máximo 1. <<

*/

	esBalanceado(nil).
	esBalanceado(a(_,HI,HD)) :- altura(HI,A1), altura(HD,A2), (abs((A1) - (A2)) =< 1),
					esBalanceado(HI),
					esBalanceado(HD).
					
/*

	altura(A,H) es cierto si H unifica con la altura del árbol A.
	
	 >> La altura de un árbol es el máximo número de nodos que 
	    existen desde la raíz hasta una de las hojas del árbol. <<
*/

%	altura(nil,0).
%	altura(a(_,HI,HD),  A) :- altura(HI,AI), altura(HD,AD), 
%				 AI >= AD, A is AI + 1.
%				 
%	altura(a(_,HI,HD),  A) :- altura(HI,AI), altura(HD,AD), 
%				 AI < AD, A is AD + 1.

	altura(nil,0).
	altura(a(_,HI,HD),  A) :- altura(HI,AI), altura(HD,AD), 
				 R is max(AI,AD), A is R + 1.
				 
%
%	cuenta_nodos(nil,0).
%
%	cuenta_nodos(a(_, HI, HD), R) :- cuenta_nodos(HI,RI), cuenta_nodos(HD,RD), R is RI + RD + 1.

