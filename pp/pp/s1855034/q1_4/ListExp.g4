grammar ListExp;
start   : listExp EOF;
listExp : listExp '+' listExp                   # concat
        | <assoc=right> listExp '::' listExp    # cons
        | '[' ( listExp ( ',' listExp )* )? ']' # list
        | '(' listExp ')'                       # paren
        | '{' (LETTER '=' listExp ';')* listExp '}'    # progexp
        | DIGIT                                 # digit
        | LETTER                                # letter
        ;

LETTER : [a-z];
DIGIT : '0'..'9';
WS : [ \t\r\n]+ -> skip;