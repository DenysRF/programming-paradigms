lexer grammar ID6;

fragment ALPHANUM : NUMBERS | LETTERS;
fragment NUMBERS :  '0'..'9';
fragment LETTERS :  'a'..'z' | 'A'..'Z';

ID : LETTERS ALPHANUM ALPHANUM ALPHANUM ALPHANUM ALPHANUM;