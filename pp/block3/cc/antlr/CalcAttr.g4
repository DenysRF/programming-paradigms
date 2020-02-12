grammar CalcAttr;

import CalcVocab;

@members {
    private int getValue(String text) {
        return Integer.parseInt(text);
    }
}

expr returns [ int val ]
     : e0=expr TIMES e1=expr
       { $val = $e0.val * $e1.val; }
     | e0=expr PLUS e1=expr
       { $val = $e0.val + $e1.val; }
     | { System.out.println("Evaluating PARENTHESES"); }
       LPAR e=expr RPAR
       { $val = $e.val; }
     | { System.out.println("Evaluating NEG"); }
       MINUS e=expr
       { $val = $e.val * -1; }
     | { System.out.println("Evaluating NUMBER"); }
       NUMBER
       { $val = getValue($NUMBER.text); }
     ;
