% Ex 12
istree(nil).
istree(t(L,_,R)) :- istree(L), istree(R).

% Ex 13
tree1(X) :- T1=t(nil,1,T3),
            T3=t(nil,3,nil),
            T4=t(T1,4,nil),
            T7=t(nil,7,nil),
            T6=t(nil,6,T7),
            T9=t(nil,9,nil),
            T8=t(T6,8,T9),
            X=t(T4,5,T8).

tree2(X) :- X = t(t(nil,1,nil),5,t(t(nil,7,nil),9,t(nil,10,nil))).

max(t(_,N,nil), N).
max(t(_,_,L), N) :- max(L,N).

min(t(nil,N,_), N).
min(t(R,_,_), N) :- min(R,N).

% Ex 14
issorted(t(nil,_,nil)).
issorted(t(     R,       _,       L)    )    :- issorted(R), issorted(L).
issorted(t(     _       ,N1,  t(_,N2,_)))    :- N1<N2.
issorted(t( t(_,N2,_)   ,N1,      _)    )    :- N1>=N2.

% Ex 15
find(t(L,N,R),N,t(L,N,R)).
find(t(L,V,_),N,S) :- N=<V, find(L,N,S).
find(t(_,V,R),N,S) :- N>V, find(R,N,S).

% Ex 16
insert(nil,N,t(nil,N,nil)).
insert(t(L,V,R),N,t(S,V,R)) :- N=<V, insert(L,N,S).
insert(t(L,V,R),N,t(L,V,S)) :- N>V, insert(R,N,S).

% Ex 17
deleteAll(T,N,T) :- not(find(T,N,_)).
deleteAll(T,N,S) :- delete(T,N,Q), deleteAll(Q,N,S).

delete(t(nil,N,R),N,R) :- !.
delete(t(L,N,nil),N,L) :- !.
delete(t(L,N,R),N,t(L,M,T)) :- min(R,M), delete(R,M,T).
delete(t(L,V,R),N,t(S,V,R)) :- N<V, delete(L,N,S).
delete(t(L,V,R),N,t(L,V,S)) :- N>V, delete(R,N,S).

% Ex 18
listtree([X],S) :- insert(nil,X,S).
listtree([X,Y|Z],S) :- listtree([Y|Z],Q), insert(Q,X,S).

% Ex 19
treelist(nil,[]).
treelist(T,[V|L]) :- min(T,V), deleteAll(T,V,R), treelist(R,L).

% Ex 20
treesort(L1,L2) :- listtree(L1,S), treelist(S,L2).
