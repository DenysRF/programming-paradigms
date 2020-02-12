lexer grammar La;

LA : 'L' 'a'+ ' '*;

LA_LA : LA LA;

LA_LA_LA_LI : LA LA LA 'L' 'i' (' ')*;