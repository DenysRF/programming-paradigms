lexer grammar Ada;

fragment DIGIT              : [0-9];
fragment EXTENDED_DIGIT     : DIGIT | [a-fA-F];

fragment NUMERAL            : DIGIT ('_'? DIGIT)*;
fragment EXPONENT           : ('E' | 'e') '+'? NUMERAL;
fragment BASED_NUMERAL      : EXTENDED_DIGIT ('_'? EXTENDED_DIGIT)*;
fragment BASE               : ('0' '_'?)* [2-9] | ('0' '_'?)* '1' '_'? [0-6];

fragment DECIMAL_LITERAL    : NUMERAL EXPONENT?;
fragment BASED_LITERAL      : BASE '#' BASED_NUMERAL '#' EXPONENT?;

NUMBER  : DECIMAL_LITERAL | BASED_LITERAL;