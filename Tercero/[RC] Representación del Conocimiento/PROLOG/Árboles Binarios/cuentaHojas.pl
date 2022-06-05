
/*

	cuentaHojas(A,N) es cierto cuando N unifica con el número de hojas
			 del árbol A.

*/

	cuentaHojas(nil,0).
	
	cuentaHojas(A, 1) :- hoja(A).
	cuentaHojas(a(_,HI,HD), N3) :- A = a(_,HI,HD), not(hoja(A)), cuentaHojas(HI,N1), cuentaHojas(HD,N2),
				       N3 is N1 + N2.
				       
	hoja(a(_,nil,nil)).				       
				 