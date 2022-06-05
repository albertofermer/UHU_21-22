
/*

	muestraHojas(+A,-L) es cierto si L unifica con una lista donde aparecen los
			    nodos hoja del árbol A.

*/

	muestraHojas(nil,[]).
	
	muestraHojas(a(E,nil,nil), [E]). 
	muestraHojas(a(_,HI,HD), L3) :- A = a(_,HI,HD), not(hoja(A)), 
					    muestraHojas(HI,L1), 
					    muestraHojas(HD,L2),
					    append(L1,L2,L3).
	hoja(a(_,nil,nil)).						