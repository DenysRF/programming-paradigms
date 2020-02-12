package block3.cc.antlr;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class TypeCalculator extends TypeCalcBaseListener {

    private ParseTreeProperty<Type> types;

    public void init() {
        this.types = new ParseTreeProperty<>();
    }

    @Override
    public void exitHat(TypeCalcParser.HatContext ctx) {
        super.exitHat(ctx);
        Type t0 = type(ctx.expr(0));
        Type t1 = type(ctx.expr(1));
        set(ctx, getTypeHat(t0, t1));
    }

    @Override
    public void exitPlus(TypeCalcParser.PlusContext ctx) {
        super.exitPlus(ctx);
        Type t0 = type(ctx.expr(0));
        Type t1 = type(ctx.expr(1));
        set(ctx, getTypePlus(t0, t1));
    }

    @Override
    public void exitEquals(TypeCalcParser.EqualsContext ctx) {
        super.exitEquals(ctx);
        Type t0 = type(ctx.expr(0));
        Type t1 = type(ctx.expr(1));
        set(ctx, getTypeEq(t0, t1));
    }

    @Override
    public void exitParens(TypeCalcParser.ParensContext ctx) {
        super.exitParens(ctx);
        set(ctx, type(ctx.expr()));
    }

    @Override
    public void exitNum(TypeCalcParser.NumContext ctx) {
        super.exitNum(ctx);
        set(ctx, Type.NUM);
    }

    @Override
    public void exitBool(TypeCalcParser.BoolContext ctx) {
        super.exitBool(ctx);
        set(ctx, Type.BOOL);
    }

    @Override
    public void exitStr(TypeCalcParser.StrContext ctx) {
        super.exitStr(ctx);
        set(ctx, Type.STR);
    }

    /** Sets the val attribute of a given node. */
    private void set(ParseTree node, Type type) {
        this.types.put(node, type);
    }

    /** Retrieves the type attribute of a given node. */
    public Type type(ParseTree node) {
        return this.types.get(node);
    }

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
