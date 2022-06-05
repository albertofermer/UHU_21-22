/*

	reverse(Lista,Reverse) es cierto si Reverse es la lista invertida.
	[1,2,3] - [3,2,1] 
	
	
	[1,2,3] - [3, 2, 1]
	[2,3] - [3, 2]
	[3] - [3]
	[] - []
*/

mi_reverse([],[]).
mi_reverse([C|Resto],R) :- mi_reverse(Resto,R2),
				insertar_final(C,R2,R).
				

/*

	insertar_final(Elem,Lista,ListaR) es cierto
	si ListaR contiene los elementos de Lista con
	Elem al final.
	
		3 + [1,2] = [1,2,3]
		3 + [2]	= [2,3]
	
*/

insertar_final(Y,[],[Y]).
insertar_final(X,[C|R],ListaR) :- insertar_final(X,R,ListaR2),
					ListaR = [C | ListaR2].