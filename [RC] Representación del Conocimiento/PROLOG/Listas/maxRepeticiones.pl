
/*
	comprime(+Lista,-R) es cierto si R unifica con una Lista
		de la siguiente forma:
		
		{a,a,b,b,b,c,d,d} - {(a,2),(b,3),(c,1),(d,2)}	
		{a,b,b,b,c,d,d} - {(a,1),(b,3),(c,1),(d,2)}
		{b,b,b,c,d,d} - {(b,3),(c,1),(d,2)}
*/

comprime([],[]).
comprime([X],[(X,1)]).

comprime([C1,C1|Resto], [(C1,N2) |Res]) :-
				comprime([C1|Resto], [(C1,N)|Res]),
				N2 is N + 1.
				
				
comprime([C1,C2|Resto], [(C1,1), (C2,N2) |Res]) :- C1 \= C2,
				comprime([C2|Resto], [(C2,N2)|Res]).
				

/*

	mayor(+L,-Elem,N) es cierto si Elem unifica con el elemento que se repite más veces
	en la lista L.
*/
mayor([],"null",-1).
mayor([(Cab,N)| Resto],Cab,N) :- mayor(Resto,_,N2),
				   N > N2.				   
mayor([(_,N)| Resto],E,N2) :- mayor(Resto,E,N2),
				   N =< N2.

/*

	mas_veces(+Lista,-Elem,-Num) es cierto si Elem unifica con el elemento de Lista 
				     que más veces se repite y Num con el número de veces 
				     que se reptite dicho elemento.
		{a,a,a,a,b,b} - {(a,4)}

*/

mas_veces(L,Elem,N) :-  msort(L,R1),
			comprime(R1,R),
			mayor(R,Elem,N).
			
			