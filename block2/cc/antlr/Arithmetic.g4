grammar Arithmetic;

expr : OPENP expr CLOSEP                # parentheses
     | NEG expr                         # neg
     | <assoc=right> expr EXP expr      # exp
     | <assoc=left> expr MULT expr      # mult
     | <assoc=left> expr MINUS expr     # minus
     | <assoc=left> expr PLUS expr      # plus
     | NUMBER                           # number
     ;

NUMBER  : [1-9][0-9]* | [0-9] ;
PLUS    : '+' ;
NEG     : '--' ;
MINUS   : '-' ;
MULT    : '*' ;
EXP     : '^' ;
OPENP   : '(' ;
CLOSEP  : ')' ;

// ignore whitespace
WS : [ \t\n\r] -> skip;

// everything else is a typo
TYPO : [a-zA-Z]+;
