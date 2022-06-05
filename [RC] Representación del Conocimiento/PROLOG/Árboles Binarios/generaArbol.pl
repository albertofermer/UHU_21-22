
/*

	crea_ab(+Lista,-ArbolBinario)
		es cierto si ArbolBinario unifica con un árbol binario
		que contiene los elementos de Lista. El árbol resultante debe
		ser balanceado.

*/

crea_ab([],nil).

crea_ab(Lista,a(Raiz,AL,AR)):-
	length(Lista,L), M is L div 2,
	length(Left,M), append([Raiz|Left],Right,Lista),
	crea_ab(Left, AL),
	crea_ab(Right, AR).

/*

	crea_ab(+Ini,+N,-ArbolBinario)
	es cierto si ArbolBinario unifica con un árbol binario que contiene
	los numeros del 1 al N.

*/


crea_ab(N,N,a(N,nil,nil)).
crea_ab(I,N,nil) :- I > N.
crea_ab(Raiz, N, a(Raiz,Ai,Ad)) :- Raiz < N, 
				HijoIzq is Raiz * 2,
				HijoDer is Raiz * 2 + 1,
				crea_ab(HijoIzq,N,Ai),
				crea_ab(HijoDer,N,Ad).			
				
				