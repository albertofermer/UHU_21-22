
/*
	Convierte un numero peano a un numero en p2d.
	p2d(P,D) es cierto si D es el numero P en Decimal.
	
	0 - 0
	s(0) - 1
	s(s(0)) - 2
	...

*/

p2d(0,0).
p2d(s(P), D2) :- p2d(P, D1), D2 is D1 + 1. 