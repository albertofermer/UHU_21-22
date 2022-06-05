
/*

	ArbolesIguales(A,B) es cierto si B es igual al árbol A.

*/

arbolesIguales(A,A).
%	arbolesIguales(nil,nil).
%	arbolesIguales(a(E1,AI,AD), a(E2,BI,BD)) :- E1 = E2, arbolesIguales(AI,BI), arbolesIguales(AD,BD).


/*

	ArbolesSimetricos(A,B) es cierto si B es un árbol simétrico a A.

*/



arbolesSimetricos(nil).
arbolesSimetricos(A) :- espejo(A,A).

arbolesSimetricos(nil,nil).
arbolesSimetricos(A,B) :- espejo(A,B).

espejo(nil,nil).
espejo(a(E,HI,HD), a(E,HI2,HD2)) :- espejo(HI,HD2), espejo(HD,HI2). 

/*

	ArbolesIsomorfos(A,B) es cierto si B es un árbol de la misma forma que
	el árbol A.

*/

arbolesIsomorfos(nil,nil).
arbolesIsomorfos(a(_,AI,AD), a(_,BI,BD)) :- arbolesIsomorfos(AI,BI), arbolesIsomorfos(AD,BD).