
/*

	sumaNodos(+A,-S) es cierto si S unifica con la suma de las etiquetas de
	todos los nodos del árbol A.

*/

sumaNodos(nil,0).
sumaNodos(a(E,HI,HD), S) :- sumaNodos(HI,N2), sumaNodos(HD,N3), 
			   N4 is N2 + N3, 
			   S is N4 + E.