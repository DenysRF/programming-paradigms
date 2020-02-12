mother(emma,wilhelmina).
mother(wilhelmina,juliana).
mother(juliana,beatrix).
mother(juliana,margriet).
mother(juliana,irene).
mother(juliana,christina).
mother(margriet,maurits).
mother(margriet,bernhard_jr).
mother(margriet,pieter_christiaan).
mother(margriet,floris).
mother(beatrix,alexander).
mother(beatrix,friso).
mother(beatrix,constantijn).
mother(maxima,amalia).
mother(maxima,alexia).
mother(maxima,ariane).

% added to show loop Ex 4
% mother(wilhelmina, emma).
%------------------------

husband(bernhard,juliana).
husband(claus,beatrix).
husband(pieter,margriet).
husband(alexander,maxima).
husband(friso,mabel).
husband(constantijn,laurentien).

female(irene).
female(christina).
female(amalia).
female(alexia).
female(ariane).
female(X) :- mother(X,_).
female(X) :- husband(_,X).

male(maurits).
male(bernhard_jr).
male(pieter_christiaan).
male(floris).
male(X) :- husband(X,_).

% Ex 1
father(X,Y) :- husband(X,Z), mother(Z,Y).
child(X,Y) :- father(Y,X) ; mother(Y,X).
grandparent(X,Y) :- child(Z,X), child(Y,Z).

% Ex 2
sister(X,Y) :- child(X,P), female(X), child(Y,P), not(X=Y).
brother(X,Y) :- child(X,P), male(X), child(Y,P), not(X=Y).
aunt(X,Y) :- child(Y,P), sister(X,P) ; child(Y,P), brother(B,P), husband(B,X).
cousin(X,Y) :- child(X,P), aunt(P,Y).
nephew(X,Y) :- child(X,P), brother(Y,P) ; child(X,P), sister(Y,P).

% Ex 4
ancestor(X,Y) :- child(Y,X) ; child(Y,Z), ancestor(X,Z).
