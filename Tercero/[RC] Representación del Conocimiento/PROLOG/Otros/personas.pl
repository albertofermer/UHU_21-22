

/*

setof(+P,persona(X),Lista) devuelve en Lista todos los resultados del predicado persona(X) sin repeticiones.
bagof(+P,persona(X),Lista) devuelve en Lista todos los resultados del predicado persona(X) con repeticiones.

*/

persona(a).
persona(b).
persona(c).
persona(d).
persona(a).

dosV(a,b).
dosV(c,d).
dosV(e,f).

tresV(a,b,c).
tresV(d,e,f).
tresV(g,h,i).
tresV(a,d,g).
tresV(b,e,h).
tresV(c,f,i).