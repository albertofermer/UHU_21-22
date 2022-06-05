
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
					