grammar Latex;

tabular : START STARTARG row* END;
row : (cell '&')* cell SLASH;
cell : CELL?;

SLASH:      '\\\\';

START:      '\\begin{tabular}';
STARTARG:   '{' ('l' | 'r' | 'c')+ '}';
END:      '\\end{tabular}';

COMMENT:    '%' ~('\n')* '\n' -> skip;

//ROW     : ([a-z] | [A-Z] | ' ' | [0-9])+;
CELL :   (~[' \t\n\r\\{}$&#ˆ̃%']+ ~['\\{}$&#ˆ̃%']+ ~[' \t\n\r\\{}$&#ˆ_̃%']+)
        | ~[' \t\n\r\\{}$&#ˆ_̃%']+;

WS : [ \t\n\r]+ -> skip;