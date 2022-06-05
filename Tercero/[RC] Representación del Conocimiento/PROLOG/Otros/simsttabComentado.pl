/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   Simsttab -- Simplistic school time tabler
   Copyright (C) 2005-2022 Markus Triska triska@metalevel.at
   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
   For more information about this program, visit:
          https://www.metalevel.at/simsttab/
          ==================================
   Tested with Scryer Prolog.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */


:- use_module(library(clpz)).
:- use_module(library(dcgs)).
:- use_module(library(reif)).
:- use_module(library(pairs)).
:- use_module(library(lists)).
:- use_module(library(format)).
:- use_module(library(pio)).

:- dynamic(class_subject_teacher_times/4).
:- dynamic(coupling/4).
:- dynamic(teacher_freeday/2).
:- dynamic(slots_per_day/1).
:- dynamic(slots_per_week/1).
:- dynamic(class_freeslot/2).
:- dynamic(room_alloc/4).

:- discontiguous(class_subject_teacher_times/4).
:- discontiguous(class_freeslot/2).


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			 Posting constraints
   The most important data structure in this CSP are pairs of the form
      Req-Vs
   where Req is a term of the form req(C,S,T,N) (see below), and Vs is
   a list of length N. The elements of Vs are finite domain variables
   that denote the *time slots* of the scheduled lessons of Req. We
   call this list of Req-Vs pairs the requirements.
   To break symmetry, the elements of Vs are constrained to be
   strictly ascending (it follows that they are all_different/1).
   Further, the time slots of each teacher are constrained to be
   all_different/1.
   For each requirement, the time slots divided by slots_per_day are
   constrained to be strictly ascending to enforce distinct days,
   except for coupled lessons.
   The time slots of each class, and of lessons occupying the same
   room, are constrained to be all_different/1.
   Labeling is performed on all slot variables.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

/* 
1º Guarda los requisitos en Goal
2º Guarda en Rs0 la lista de todas las posibles soluciones que cumplan las restricciones no duplicadas y ordenadas
3º Ejecuta req_with_slots en todo Rs0 y en Rs como salida
*/
requirements(Rs) :-
        Goal = class_subject_teacher_times(Class,Subject,Teacher,Number),
        setof(req(Class,Subject,Teacher,Number), Goal, Rs0),
        maplist(req_with_slots, Rs0, Rs).

/*
Asignar a cada restriccion una lista de N huecos
*/
req_with_slots(R, R-Slots) :- 
        R = req(_,_,_,N), length(Slots, N).

/*
Devuelve en "Classes" la lista de distintas clases que hay ordenadas sin repetir
*/
classes(Classes) :-
        setof(C, S^N^T^class_subject_teacher_times(C,S,T,N), Classes).

/*
Devuelve en "Teachers" la lista de distintos profesores que hay ordenadas sin repetir
*/
teachers(Teachers) :-
        setof(T, C^S^N^class_subject_teacher_times(C,S,T,N), Teachers).

/*
Devuelve en "Rooms" la lista de aulas que hay ordenadas sin repetir
*/
rooms(Rooms) :-
        findall(Room, room_alloc(Room,_C,_S,_Slot), Rooms0),
        sort(Rooms0, Rooms).

/*
1º Consigue las posibles soluciones en Rs que cumplen los requisitos seguido de una lista vacía de las soluciones
2º Guarda en Vars la lista vacía del par Requisito-Solucion
3º Guarda en SPM huecos por semana
4º Instancia en Max el numero de huecos semanales - 1
5º Establece el dominio máximo de las variables
6º Aplica las restricciones a las asignaturas sucesivas (continuas)
7º Instancia las clases
8º Instancia los profesores
9º Instancia las aulas
10º Aplica las restricciones a los profesores según las restricciones de asignaturas
11º Aplica las restricciones a los clases según las restricciones de asignaturas
12º Aplica las restricciones a los aulas según las restricciones de asignaturas
*/
requirements_variables(Rs, Vars) :-
        requirements(Rs),
        pairs_slots(Rs, Vars),
        slots_per_week(SPW),
        Max #= SPW - 1,
        Vars ins 0..Max,
        maplist(constrain_subject, Rs),
        classes(Classes),
        teachers(Teachers),
        rooms(Rooms),
        maplist(constrain_teacher(Rs), Teachers),
        maplist(constrain_class(Rs), Classes),
        maplist(constrain_room(Rs), Rooms).

/*
1º Instancia en SPD los huecos por día
2º Establece en Q el cociente en fracción de lo que ocupa del total por día
*/
slot_quotient(S, Q) :-
        slots_per_day(SPD),
        Q #= S // SPD.

/*
[list_without_nths, without_, without_at_pos0]
Elimina de la lista Es0 los valores de los indices de Ws y unifica en Es
*/
list_without_nths(Es0, Ws, Es) :-
        phrase(without_(Ws, 0, Es0), Es).
without_([], _, Es) --> seq(Es).
without_([W|Ws], Pos0, [E|Es]) -->
        { Pos #= Pos0 + 1,
          zcompare(R, W, Pos0) },
        without_at_pos0(R, E, [W|Ws], Ws1),
        without_(Ws1, Pos, Es).
without_at_pos0(=, _, [_|Ws], Ws) --> [].
without_at_pos0(>, E, Ws0, Ws0) --> [E].

%:- list_without_nths([a,b,c,d], [3], [a,b,c]).
%:- list_without_nths([a,b,c,d], [1,2], [a,d]).

/*
1º En S1 se establece el índice de la posicion que ocupa en Slots F
2º En S2 se establece el índice de la posicion que ocupa en Slots S
3º Establece que S2 será S1 + 1 para indicar que son 2 clases seguidas
*/
slots_couplings(Slots, F-S) :-
        nth0(F, Slots, S1),
        nth0(S, Slots, S2),
        S2 #= S1 + 1.
        
/*
1º Establece el orden creciente estricto de los slots vacios
2º Por cada requisito, saca el coeficiente (en fraccion) de los huecos del requisito
3º Guarda en Cs todas las clases que son sucesivas de este requisito (clase y asignatura)
4º Comprueba que se cumpla que las clases sean sucesivas las de este requisito (clase y asignatura)
5º Elimina de la Hash table Cs los Keys para quedarse en Seconds0 solo con los values
6º Ordena Seconds0 ascendentemente en Seconds
7º Elimina de la lista Qs0 las posiciones Seconds en Qs (clases siguientes sucesivas)
8º Establece el orden creciente estricto de Qs (clases siguientes sucesivas)
*/
constrain_subject(req(Class, Subj, _Teacher, _Num)-Slots) :-
        strictly_ascending(Slots), % break symmetry
        maplist(slot_quotient, Slots, Qs0),
        findall(F-S, coupling(Class,Subj,F,S), Cs),
        maplist(slots_couplings(Slots), Cs),
        pairs_values(Cs, Seconds0),
        sort(Seconds0, Seconds),
        list_without_nths(Qs0, Seconds, Qs),
        strictly_ascending(Qs).

/*
Es cierto cuando los valores de F son distintos de Vs
*/
all_diff_from(Vs, F) :- 
        maplist(#\=(F), Vs).

/*
1º Filtra según los requisitos los huecos que tiene cada clase para cada asignatura
2º Guarda en Vs la lista vacía del par Clase-Asignatura 
3º Restringe para sean únicos los valores de asignaturas de Vs
4º Encuentra los huecos que haya para la clase teniendo en cuenta las horas que la clase no
curse asignaturas
5º Se comprueba que los valores de Vs son todos distintos de Frees
*/
constrain_class(Rs, Class) :-
        tfilter(class_req(Class), Rs, Sub),
        pairs_slots(Sub, Vs),
        all_different(Vs),
        findall(S, class_freeslot(Class,S), Frees),
        maplist(all_diff_from(Vs), Frees).

/*
[tfilter filtra Rs por la condicion 
teacher_req(Teacher) y lo guarda en Sub]
1º Filtra según los requisitos los huecos que tiene cada profesor para cada asignatura
2º Guarda en Vs la lista vacía del par Profesor-Asignatura
3º Restringe para sean únicos los valores de asignaturas de Vs
4º Encuentra los huecos que haya para el profesor teniendo en cuenta los días que no trabaja
5º Por cada asignatura, saca el coeficiente (en fraccion) de los huecos de la asignatura en Qs
6º Se comprueba que los valores de Qs son todos distintos de Fs
*/
constrain_teacher(Rs, Teacher) :-
        tfilter(teacher_req(Teacher), Rs, Sub),
        pairs_slots(Sub, Vs),
        all_different(Vs),
        findall(F, teacher_freeday(Teacher, F), Fs),
        maplist(slot_quotient, Vs, Qs),
        maplist(all_diff_from(Qs), Fs).

/*
1º Comprueba que el par requisito-slot pertenece a la lista de requisitos Reqs
2º En Var se establece el índice de la posicion que ocupa en Slots Lesson
*/
sameroom_var(Reqs, r(Class,Subject,Lesson), Var) :-
        memberchk(req(Class,Subject,_Teacher,_Num)-Slots, Reqs),
        nth0(Lesson, Slots, Var).

/*
1º Busca las habitaciones que cumplan las restricciones RReqs
2º Unifica cuando las habitaciones distintas cumplen los requisitos
3º Restringe para sean únicos los valores de aulas de Roomvars
*/
constrain_room(Reqs, Room) :-
        findall(r(Class,Subj,Less), room_alloc(Room,Class,Subj,Less), RReqs),
        maplist(sameroom_var(Reqs), RReqs, Roomvars),
        all_different(Roomvars).

/*
#< es una lista de variables de dominio finito que son 
una cadena con respecto al orden parcial Ls,
en el orden en que aparecen en la lista.
*/
strictly_ascending(Ls) :- 
        chain(#<, Ls).

/*
Se cumple cumple cuando C0, C1 y T son iguales
*/
class_req(C0, req(C1,_S,_T,_N)-_, T) :- 
        =(C0, C1, T).

/*
Se cumple cumple cuando T0, T1 y T son iguales
*/
teacher_req(T0, req(_C,_S,T1,_N)-_, T) :- 
        =(T0,T1,T).

/*
1º Elimina de la Hash table Ps los Keys para quedarse en Vs0 solo con los values
2º Une la lista de values de Vs0 a la lista de Vs
*/
pairs_slots(Ps, Vs) :-
        pairs_values(Ps, Vs0),
        append(Vs0, Vs).


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   Relate teachers and classes to list of days.
   Each day is a list of subjects (for classes), and a list of
   class/subject terms (for teachers). The predicate days_variables/2
   yields a list of days with the right dimensions, where each element
   is a free variable.
   We use the atom 'free' to denote a free slot, and the compound terms
   class_subject(C, S) and subject(S) to denote classes/subjects.
   This clean symbolic distinction is used to support subjects
   that are called 'free', and to improve generality and efficiency.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

/*
1º Instancia en SPW los huecos por semana
2º Instancia en SPD los huecos por día
3º Instancia en NumDays la division entera de huecos por semana 
entre huecos por día para obtener los días totales
4º Instancia una lista Days de NumDays elementos
5º Instancia una lista Day de SPD elementos
6º Toma los huecos de un día y los replica como días haya en la semana
7º Une a Vs la lista Days
*/
days_variables(Days, Vs) :-
        slots_per_week(SPW),
        slots_per_day(SPD),
        NumDays #= SPW // SPD,
        length(Days, NumDays),
        length(Day, SPD),
        maplist(same_length(Day), Days),
        append(Days, Vs).


class_days(Rs, Class, Days) :-
        days_variables(Days, Vs),
        tfilter(class_req(Class), Rs, Sub),
        foldl(v(Sub), Vs, 0, _).

v(Rs, V, N0, N) :-
        (   member(req(_,Subject,_,_)-Times, Rs),
            member(N0, Times) -> V = subject(Subject)
        ;   V = free
        ),
        N #= N0 + 1.

teacher_days(Rs, Teacher, Days) :-
        days_variables(Days, Vs),
        tfilter(teacher_req(Teacher), Rs, Sub),
        foldl(v_teacher(Sub), Vs, 0, _).

v_teacher(Rs, V, N0, N) :-
        (   member(req(C,Subj,_,_)-Times, Rs),
            member(N0, Times) -> V = class_subject(C, Subj)
        ;   V = free
        ),
        N #= N0 + 1.

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   Print objects in roster.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

print_classes(Rs) :-
        classes(Cs),
        phrase_to_stream(format_classes(Cs, Rs), user_output).

format_classes([], _) --> [].
format_classes([Class|Classes], Rs) -->
        { class_days(Rs, Class, Days0),
          transpose(Days0, Days) },
        format_("Class: ~w~2n", [Class]),
        weekdays_header,
        align_rows(Days),
        format_classes(Classes, Rs).

align_rows([]) --> "\n\n\n".
align_rows([R|Rs]) -->
        align_row(R),
        "\n",
        align_rows(Rs).

align_row([]) --> [].
align_row([R|Rs]) -->
        align_(R),
        align_row(Rs).

align_(free)               --> align_(verbatim('')).
align_(class_subject(C,S)) --> align_(verbatim(C/S)).
align_(subject(S))         --> align_(verbatim(S)).
align_(verbatim(Element))  --> format_("~t~w~t~8+", [Element]).

print_teachers(Rs) :-
        teachers(Ts),
        phrase_to_stream(format_teachers(Ts, Rs), user_output).

format_teachers([], _) --> [].
format_teachers([T|Ts], Rs) -->
        { teacher_days(Rs, T, Days0),
          transpose(Days0, Days) },
        format_("Teacher: ~w~2n", [T]),
        weekdays_header,
        align_rows(Days),
        format_teachers(Ts, Rs).

weekdays_header -->
        { maplist(with_verbatim,
                  ['Mon','Tue','Wed','Thu','Fri'],
                  Vs) },
        align_row(Vs),
        format_("~n~`=t~40|~n", []).

with_verbatim(T, verbatim(T)).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   ?- consult('reqs_example.pl'),
      requirements_variables(Rs, Vs),
      labeling([ff], Vs),
      print_classes(Rs).
   %@ Class: 1a
   %@
   %@   Mon     Tue     Wed     Thu     Fri
   %@ ========================================
   %@   mat     mat     mat     mat     mat
   %@   eng     eng     eng
   %@    h       h
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */