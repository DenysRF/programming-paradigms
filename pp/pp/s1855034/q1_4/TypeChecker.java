package pp.s1855034.q1_4;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.List;

public class TypeChecker extends ListExpBaseListener {



    public static class Type {
        private String type;
        private int depth;
        public Type(String type) {
            this.type = type;
        }
        public Type(String type, int depth) {
            this.type = type;
            this.depth = depth;
        }
        public String getType() {
            return this.type;
        }
        public int getDepth() {
            return this.depth;
        }
    }

    private ParseTreeProperty<Type> types;
    private StringBuilder errors;
    private MySymbolTable st;

    public void init() {
        this.types = new ParseTreeProperty<>();
        this.errors = new StringBuilder();
        this.st = new MySymbolTable();

    }

    @Override
    public void exitConcat(ListExpParser.ConcatContext ctx) {
        Type t0 = type(ctx.listExp(0));
        Type t1 = type(ctx.listExp(1));

        Type newType = getTypeConcat(t0, t1);
        set(ctx, newType);

        if (newType.type.equals("ERR")) {
            int line = ctx.getStart().getLine();
            int pos = ctx.getStart().getCharPositionInLine();
            String s = "\t" + ctx.getText() + " - at line " + line + ", position " + pos +
                    ":\n\tconcat was applied to (L) " + t0.getType() + " and (R) " +
                    t1.getType() + ", but should be applied to two lists.\n";
            errors.append(s);
        }

    }

    @Override
    public void exitCons(ListExpParser.ConsContext ctx) {
        Type t0 = type(ctx.listExp(0));
        Type t1 = type(ctx.listExp(1));

        Type newType = getTypeCons(t0, t1);
        set(ctx, newType);

        if (newType.getType().equals("ERR")) {
            int line = ctx.getStart().getLine();
            int pos = ctx.getStart().getCharPositionInLine();
            String s = "\t" + ctx.getText() + " - at line " + line + ", position " + pos +
                    ":\n\tconcat was applied to (L) " + t0.getType() + " and (R) " +
                    t1.getType() + ", but should be applied to a list at the right.\n";
            errors.append(s);
        }
    }

    @Override
    public void exitList(ListExpParser.ListContext ctx) {
        List<ListExpParser.ListExpContext> l = ctx.listExp();
        Type t = null;
        for (ListExpParser.ListExpContext c : l) {
            if (t == null) {
                t = type(c);
            } else if (type(c).getDepth() > t.getDepth()) {
                t = type(c);
            }
        }
        set(ctx, getTypeList(t));
    }

    @Override
    public void exitParen(ListExpParser.ParenContext ctx) {
        set(ctx, type(ctx.listExp()));
    }

    @Override
    public void exitDigit(ListExpParser.DigitContext ctx) {
        set(ctx, new Type("DIGIT"));
    }

    @Override
    public void exitLetter(ListExpParser.LetterContext ctx) {
        set(ctx, new Type("LETTER"));
    }

    @Override
    public void enterProgexp(ListExpParser.ProgexpContext ctx) {
        st.openScope();
    }

    @Override
    public void exitProgexp(ListExpParser.ProgexpContext ctx) {
        st.closeScope();
    }

    private void set(ParseTree node, Type type) {
        this.types.put(node, type);
    }

    public Type type(ParseTree node) {
        return this.types.get(node);
    }

    private Type getTypeCons(Type t0, Type t1) {
        if ((t0.getType().equals("DIGIT") || t0.getType().equals("LETTER")) && t1.getType().equals("LIST")) {
            return new Type("LIST", t1.getDepth());
        } else if (t0.getType().equals("LIST") && t1.getType().equals("LIST")) {
            return new Type("LIST", t1.getDepth() + 1);
        } else {
            return new Type("ERR");
        }
    }

    private Type getTypeConcat(Type t0, Type t1) {
        if (t0.getType().equals("LIST") && t1.getType().equals("LIST")) {
            return new Type("LIST", (t0.getDepth() >= t1.getDepth()) ? t0.getDepth() : t1.getDepth());
        } else {
            return new Type("ERR");
        }
    }

    private Type getTypeList(Type t) {
        if (t != null) {
            return new Type("LIST", t.getDepth() + 1);
        } else {
            return new Type("LIST", 1);
        }

    }

    public String getErrors() {
        if (errors.length() == 0) {
            return "No Errors!";
        } else {
            errors.insert(0, "Errors:\n");
            return this.errors.toString();
        }
    }
}
