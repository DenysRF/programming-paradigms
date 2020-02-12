grammar TypeCalcAttr;

@members {
    private Type getTypeHat(Type t0, Type t1) {
        return (t0 == Type.NUM && t1 == Type.NUM) ? Type.NUM :
                        (t0 == Type.STR && t1 == Type.NUM) ? Type.STR :
                                Type.ERR;
    }

    private Type getTypePlus(Type t0, Type t1) {
        return (t0 == t1) ? t0 : Type.ERR;
    }

    private Type getTypeEq(Type t0, Type t1) {
        return (t0 == t1) ? Type.BOOL : Type.ERR;
    }
}

expr returns [ Type type ]
    : e0=expr HAT e1=expr
      { $type = getTypeHat($e0.type, $e1.type); }
    | e0=expr PLUS e1=expr
      { $type = getTypePlus($e0.type, $e1.type); }
    | e0=expr EQUALS e1=expr
      { $type = getTypeEq($e0.type, $e1.type); }
    | LPAR e=expr RPAR
      { $type = $e.type; }
    | NUM
      { $type = Type.NUM; }
    | BOOL
      { $type = Type.BOOL; }
    | STR
      { $type = Type.STR; }
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