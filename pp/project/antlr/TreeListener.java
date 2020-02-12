package project.antlr;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.HashMap;

/**
 * Handles type-checking and tracks type errors
 * @author denys, yernar
 */
public class TreeListener extends LanguageBaseListener {

    private StringBuilder errors = new StringBuilder("Errors:\n");

    private HashMap<String, Type> varTypes = new HashMap<>();

    private ParseTreeProperty<Type> types = new ParseTreeProperty<>();

    /**
     * Adds the combined type of an addition expression to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitAddExpr(LanguageParser.AddExprContext ctx) {
        Type t0 = this.types.get(ctx.expr(0));
        Type t1 = this.types.get(ctx.expr(1));
        this.types.put(ctx, getTypeAdd(t0, t1, ctx));
    }

    /**
     * Adds the combined type of a multiplication expression to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitMultExpr(LanguageParser.MultExprContext ctx) {
        Type t0 = this.types.get(ctx.expr(0));
        Type t1 = this.types.get(ctx.expr(1));
        this.types.put(ctx, getTypeMult(t0, t1, ctx));
    }

    /**
     * Adds the combined type of a comparison expression to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitCompExpr(LanguageParser.CompExprContext ctx) {
        Type t0 = this.types.get(ctx.expr(0));
        Type t1 = this.types.get(ctx.expr(1));
        this.types.put(ctx, getTypeComp(t0, t1, ctx));
    }

    /**
     * Adds the type of the condition in an if(else) statement to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitIfStat(LanguageParser.IfStatContext ctx) {
        Type t = Type.BOOL;
        if (this.types.get(ctx.expr()) != Type.BOOL) {
            t = Type.ERR;
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.expr().getText());
        }
        this.types.put(ctx, t);
    }

    /**
     *  Adds the type of a constant to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitConstExpr(LanguageParser.ConstExprContext ctx) {
        if ((ctx.FALSE() != null || ctx.TRUE() != null)) {
            this.types.put(ctx, Type.BOOL);
        } else if ((ctx.NUM() != null)) {
            this.types.put(ctx, Type.INT);
        } else {
            this.types.put(ctx, Type.ERR);
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.getText());
        }
    }

    @Override
    public void exitId(LanguageParser.IdContext ctx) {
        types.put(ctx, varTypes.get(ctx.ID().getText()));
    }

    @Override
    public void enterDecl(LanguageParser.DeclContext ctx) {
        if (ctx.type().getText().equals("bool")) {
            this.types.put(ctx.ID(), Type.BOOL);
            varTypes.put(ctx.ID().getText(), Type.BOOL);
        } else if (ctx.type().getText().equals("int")) {
            this.types.put(ctx.ID(), Type.INT);
            varTypes.put(ctx.ID().getText(), Type.INT);
        }
    }

    /**
     * Adds the type of an expression within parentheses to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitParExpr(LanguageParser.ParExprContext ctx) {
        this.types.put(ctx, this.types.get(ctx.expr()));
    }

    /**
     * Adds the type of the condition in a while statement to the types parse tree.
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitWhileStat(LanguageParser.WhileStatContext ctx) {
        Type t = Type.BOOL;
        if (this.types.get(ctx.expr()) != Type.BOOL) {
            t = Type.ERR;
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.expr().getText());
        }
        this.types.put(ctx, t);
    }

    /**
     * @return the string of generated type errors.
     */
    public String getErrors() {
        return this.errors.toString();
    }

    /**
     * Retrieves the type of a given node.
     * @param node The node of which we want to know the type.
     * @return the type of the given node
     */
    public Type type(ParseTree node) {
        return this.types.get(node);
    }

    /**
     * @param t0 the type of the left argument of the addition expression
     * @param t1 the type of the right argument of the addition expression
     * @param ctx the context of the calling rule
     * @return the type resulting from combining the two given types with addition
     */
    private Type getTypeAdd(Type t0, Type t1, LanguageParser.AddExprContext ctx) {
        if (t0 == Type.INT && t1 == Type.INT) {
            return Type.INT;
        } else {
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.expr(0).getText() + "+" + ctx.expr(1).getText());
            return Type.ERR;
        }
    }

    /**
     * @param t0 the type of the left argument of multiplication expression
     * @param t1 the type of the right argument of the multiplication expression
     * @param ctx the context of the calling rule
     * @return the type resulting from combining the two given arguments with multiplication
     */
    private Type getTypeMult(Type t0, Type t1, LanguageParser.MultExprContext ctx) {
        if (t0 == Type.INT && t1 == Type.INT) {
            return Type.INT;
        } else {
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.expr(0).getText() + "*" + ctx.expr(1).getText());
            return Type.ERR;
        }
    }

    /**
     * @param t0 the type of the left argument of the comparison expression
     * @param t1 the type of the right argument of the comparison expression
     * @param ctx the context of the calling rule
     * @return the type resulting from combining the two given arguments with comparison
     */
    private Type getTypeComp(Type t0, Type t1, LanguageParser.CompExprContext ctx) {
        if (t0 == t1 && t0 != Type.ERR) {
            return Type.BOOL;
        } else {
            Token token = ctx.getStart();
            addError(token.getLine(), token.getCharPositionInLine(), ctx.expr(0).getText() + ctx.getChild(1).getText() + ctx.expr(1).getText());
            return Type.ERR;
        }
    }

    /**
     * Adds an error message to the errors
     * @param line the line number where the error occurs in the program
     * @param pos the character position in the line where the error occurred in the program
     * @param ctx the context of the calling rule
     */
    private void addError(int line, int pos, String ctx) {
        String s = (ctx + " at line " + line + ", position " + pos +
                ": resulted in a type error.\n");
        errors.append(s);
    }

}
