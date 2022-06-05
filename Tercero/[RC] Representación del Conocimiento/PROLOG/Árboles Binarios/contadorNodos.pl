
/*

	cuenta_nodos(+Arbol, -NumNodos)
		es cierto si NumNodos unifica con el número de nodos de Arbol.

*/

cuenta_nodos(nil,0).

cuenta_nodos(a(_, HI, HD), R) :- cuenta_nodos(HI,RI), cuenta_nodos(HD,RD), R is RI + RD + 1.


	arbol1(
		a(1, a(2, a(4,a(7,nil,nil),nil), a(5,nil,nil)), a(3, a(6, nil, nil), nil))
	
	).
