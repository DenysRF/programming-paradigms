:- [tests].
:- use_module(library(clpfd)).
:- set_prolog_flag(clpfd_monotonic, true).


snake(RowClues, ColClues, Grid, Solution) :- copyGrid(Grid, Solution),!
                                            , checkRowClues(Solution,RowClues)
                                            , transpose(Solution,T), checkRowClues(T,ColClues) % Checks ColClues
                                            , countNeighbors(Solution) % heads have 1 neighbor, midpoints 2
                                            , nonTouching(Solution) % snake cannot touch itself
                                            , snakeConnected(Solution) % snake must be connected
                                            .

% makes a copy of a given grid, such that -1's are replaced by _'s
% [Given by manual]
copyGrid([],[]).
copyGrid([Row|G],[RowS|S]) :- copyRow(Row,RowS), copyGrid(G,S).

% Changed such that input values can only be 0 or 2
% This branches super early, so we should instantiate in
% individual checks because performance
copyRow([],[]).
% copyRow([-1|R],[_|S]) :- copyRow(R,S).
copyRow([-1|R],[X|S]) :- X in 0 \/ 2, copyRow(R,S).
copyRow([Clue|R],[Clue|S]) :- copyRow(R,S).

% Checks for every Row whether the amount of cells
% in the row mathces the clue
checkRowClues([],[]).
checkRowClues([S|Solution],[C|RowClues]) :-
    C\=(-1), checkOneRowClue(S,C), checkRowClues(Solution,RowClues);
    C==(-1), checkRowClues(Solution,RowClues).

checkOneRowClue(Row,Clue) :-
    homogenize(Row,NewRow),
    sum(NewRow,#=,Clue).

% under the assumption that these lists are filled with
% 0s, 1s and 2s, make the 2s into 1s for checkRowClues
homogenize([],[]).
homogenize([0|List], [0|R]) :- homogenize(List,R).
homogenize([1|List], [1|R]) :- homogenize(List,R).
homogenize([2|List], [1|R]) :- homogenize(List,R).

% check if every node has the correct number of neighbors
% by calling check_every_row over the 0-extended grid
countNeighbors(Solution) :-
    extend_grid(Solution, E),
    check_every_row(E).

% recursive call for check_neighbors_rows
check_every_row([X,Y,Z]) :-
    check_neighbors_rows(X,Y,Z).
check_every_row([X,Y,Z|R]) :-
    check_neighbors_rows(X,Y,Z),
    check_every_row([Y,Z|R]).

% for a cell, check whether is has the right amount of neighbors
% head/tail have 1 neighbor while midpoints have 2
% [Given by manual]
check_neighbors_pattern(0,_,_,_,_).
check_neighbors_pattern(Piece,N,E,S,W) :- 1 #=< Piece,
    count_cell(N,X1),
    count_cell(E,X2),
    count_cell(S,X3),
    count_cell(W,X4),
    Piece #= X1+X2+X3+X4.


% if cell is head or body add to neighbor count
count_cell(0,0).
count_cell(1,1).
count_cell(2,1).

% checks neighbors for all cells in rows, 3 rows at a time
% [Given by manual (except for base case)]
check_neighbors_rows([_,N,_],[W,M,E],[_,S,_]) :-
    check_neighbors_pattern(M,N,E,S,W).
check_neighbors_rows([_,N,A3|RowA],[W,M,E|RowB],[_,S,C3|RowC]) :-
    check_neighbors_pattern(M,N,E,S,W),
    check_neighbors_rows([N,A3|RowA],[M,E|RowB],[S,C3|RowC]).

% extend a row by adding a 0 at both ends
% [Given by manual]
extend_row(OldRow,NewRow) :- append([0|OldRow],[0],NewRow).

% a grid is a list of rows, this extends all rows
extend_grid_rows([],[]).
extend_grid_rows([R|Rows],[E|S]) :-
    extend_row(R,E),
    extend_grid_rows(Rows,S).

% to accomodate for neighbor checking add 0s to the edges
% of the grid
% [Given by manual]
extend_grid(OldGrid,NewGrid) :-
    transpose(OldGrid,TransGrid),
    extend_grid_rows(TransGrid,RowTransGrid),
    transpose(RowTransGrid,RowGrid),
    extend_grid_rows(RowGrid,NewGrid).


% checking if the snake does not touch its own body
nonTouching([X,Y]):-
    checkNonTouching(X,Y).
nonTouching([X,Y|Z]):-
    checkNonTouching(X,Y),
    nonTouching([Y|Z]).

% for each 2 rows we check if the snake diagonally touches itself.
checkNonTouching([X1,X2],[Y1,Y2]):-
    checkDiagonal(X1,X2,Y1,Y2).
checkNonTouching([X1,X2|R1],[Y1,Y2|R2]) :-
    checkDiagonal(X1,X2,Y1,Y2),
    checkNonTouching([X2|R1], [Y2|R2]).

% following predicate allows all the possible combination of cells
% except 8 cases (the strcuture of all cases can be found in the pdf)
% where snake body or head touches its own body
checkDiagonal(2,X,Y,Z):-
    X #=0, Y#=0, Z#=0;
    X #=0, Y#\=0;
    X#\=0.
checkDiagonal(1,X,Y,Z):-
    X #=0, Y#=0, Z#=0;
    X #=0, Y#\=0;
    X#\=0.
checkDiagonal(0,X,Y,Z):-
    X#=0;
    X#=1, Z#=0, Y#=0;
    X#=1, Z#\=0;
    X#=2, Z#=0, Y#=0;
    X#=2, Z#\=0.

% unimplemented
snakeConnected(_).
