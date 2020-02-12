grammar TypeCalc;

expr : expr HAT expr    # hat
     | expr PLUS expr   # plus
     | expr EQUALS expr # equals
     | LPAR expr RPAR   # parens
     | NUM              # num
     | BOOL             # bool
     | STR              # str
     ;

HAT     : '^';
PLUS    : '+';
EQUALS  : '=';
LPAR    : '(';
RPAR    : ')';

NUM     : [1-9][0-9]* | [0-9];
BOOL    : 'true' | 'false';
STR     : ([a-z] | [A-Z])+;

// ignore whitespace
WS : [ \t\n\r] -> skip;