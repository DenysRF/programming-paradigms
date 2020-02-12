grammar Language;

program: stat+ EOF;

stat: SHARED? type ID (ASSIGN expr)? SEMI                               #decl
    | expr ASSIGN expr SEMI                                             #assign
    | IF expr stat (ELSE stat)?                                         #ifStat
    | WHILE expr stat                                                   #whileStat
    | LCURLY stat* RCURLY                                               #blockStat
    | FORK stat                                                         #forkStat // stat here should be blockStat
    | JOIN SEMI                                                         #joinStat
    | PRINT expr SEMI                                                   #print
    ;


expr: LPAR expr RPAR                                                    #parExpr
    | expr MULT expr                                                    #multExpr
    | expr (ADD | MINUS) expr                                           #addExpr
    | expr (LeT | GrT | EQual | NEQual | LEq | GEq) expr                #compExpr
    | (NUM | TRUE | FALSE)                                              #constExpr
    | ID                                                                #id
    ;

type: INT | BOOL    #type
    ;

DOLLAR:     '$';
LCURLY:     '{';
RCURLY:     '}';
LPAR:       '(';
RPAR:       ')';
MULT:       '*';
ADD:        '+';
MINUS:      '-';
ASSIGN:     '<-';
SEMI:       ';';
UNDERSCORE: '_';

// ------- Reserved Keywords ----------
TRUE:       'true';
FALSE:      'false';
WHILE:      'while';
IF:         'if';
ELSE:       'else';
FORK:       'fork';
JOIN:       'join';
PRINT:      'print';
SHARED:     'shared';
// Basic Types
BOOL:       'bool';
INT:        'int';
// -------------------------------------


EQual:       '=';
LEq:         '<=';
GEq:         '>=';
LeT:         '<';
GrT:         '>';
NEQual:      'not=';

ID: LETTER (UNDERSCORE | LETTER | DIGIT)*;                  // variable id's start with a letter followed by any letter/digit/underscore
NUM: '0' | MINUS? [1-9] DIGIT*;                             // numbers other than 0 cannot start with a 0

fragment LETTER:    [a-zA-Z];
fragment DIGIT:     [0-9];

// Skipped token types
COMMENT: ('$' (~'{')*? '\n' | '${' .*? '}$') -> skip;      // $ ... for single line ${ ... }$ for block
WS: [ \t\r\n]+ -> skip;