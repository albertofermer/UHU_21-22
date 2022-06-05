

/*

	divisores(+X,+Y,-L) es cierto si L unifica con una lista que
	contiene a los divisores de X cuyo divisor se encuentre entre 1
	e Y.
	
	10,7 -> {1,2,5}
	10,6 -> {1,2,5}
	10,5 -> {1,2,5}
	10,4 -> {1,2}
	10,3 -> {1,2}
	10,2 -> {1,2}
	10,1 -> {1}
	

*/

divisores(_,1,[1]). /* Cualquier numero tiene como divisor al 1.*/

divisores(X,Y, [Y|R]) :- 	Y > 1,
				Y2 is Y-1,
				0 is X mod Y,		/* Si un número Y es divisor,*/
				divisores(X,Y2,R).	/* Se añade a la lista*/
				
				

divisores(X,Y, R) :- 		Y > 1,

				Y2 is Y-1,
				Z is X mod Y,		/* Si no es divisor, no se hace*/
				divisores(X,Y2,R),	/* nada. Evita la hipótesis del*/
							/* mundo cerrado.*/
				Z \= 0.


			

/*

	primo(+X) es cierto si X unifica con un número primo.

*/

primo(X) :- divisores(X,X,[X,1]). /* Un numero X es primo si tiene como divisores
					a 1 y a él mismo.*/

/*

	primosEntreXeY(+X,+Y,-L) es cierto si L unifica con una lista
	cuyos elementos son los primos que van desde X hasta Y ambos incluidos.
	
	1,10 - {2,3,5,7}
	2,10 - {2,3,5,7}
	3,10 - {3,5,7}
	

*/

	primosEntreXeY(X,X,[X]) :- primo(X).
	primosEntreXeY(X,X,[]) :- \+ primo(X).
	
	primosEntreXeY(X,Y,[X | R]) :- X < Y,
				 primo(X),
				 X2 is X + 1,
				 primosEntreXeY(X2,Y,R).
				 
	primosEntreXeY(X,Y,R) :- X < Y,
				 \+ primo(X),
				 X2 is X + 1,
				 primosEntreXeY(X2,Y,R).
				