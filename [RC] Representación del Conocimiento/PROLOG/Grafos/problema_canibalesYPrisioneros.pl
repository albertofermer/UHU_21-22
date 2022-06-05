/*

Estado(MD,CD,Barca).

Estado Inicial:
	Ei -> estado(3,3,dcha).

Meta:
	Ef -> estado(0,0,izq).

Operadores:
	mov(llevar(NM,NC,Dir), estadoAnterior, estadoDespues).
	
	mov(llevar(NM,NC,izq), estado(MD,CD, dcha), (,izq)) :- 
		MD2 is MD - NM,
		CD2 is CD - NC,

		valido(estado(NM2,CD2,izq),)
*/
mov(llevar(NM,NC,izq), estado(MD,CD, dcha), estado(MD2,CD2,izq)) :- 
		MD2 is MD - NM,
		CD2 is CD - NC,
		valido(estado(MD2,CD2,izq)).

mov(llevar(NM,NC,dcha), estado(MD,CD, izq), estado(MD2,CD2,dcha)) :- 
		MD2 is MD + NM,
		CD2 is CD + NC,
		valido(estado(MD2,CD2,dcha)).
		
llevar(1,0,_).
llevar(1,1,_).
llevar(2,0,_).
llevar(0,1,_).
llevar(0,2,_).


valido(estado(MD,CD,_)) :- MD > 0, MD >= CD, MI is 3 - MD, CI is 3 - CD,
				MI >= CI, CI > 0.
valido(estado(0,_,_)).
valido(estado(3,_,_)).


camino(I,I,_,[]).
camino(Ei, Ef, Visitados, [llevar(M,C,Dir)|Cam]) :- mov(llevar(M,C,Dir),Ei,S), 
				\+ member(S,Visitados),
				camino(S,Ef, [S|Visitados], Cam).






