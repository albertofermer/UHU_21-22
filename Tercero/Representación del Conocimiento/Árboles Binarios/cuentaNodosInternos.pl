
/*

	cuentaNodosInternos(+A,-N) es cierto si N unifica con el número de nodos internos
	del árbol A.

*/

cuentaNodosInternos(nil,0).

cuentaNodosInternos(a(_,nil,nil),0).
cuentaNodosInternos(a(_,HI,HD), N4) :-  A = a(_,HI,HD), not(hoja(A)), 
					cuentaNodosInternos(HI,N1), 
					cuentaNodosInternos(HD,N2),
					N3 is N1 + N2, N4 is N3 + 1.
					
hoja(a(_,nil,nil)).					