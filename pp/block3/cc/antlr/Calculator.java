package block3.cc.antlr;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import block3.cc.antlr.CalcParser.NumberContext;
import block3.cc.antlr.CalcParser.ParContext;
import block3.cc.antlr.CalcParser.PlusContext;
import block3.cc.antlr.CalcParser.TimesContext;

public class Calculator extends CalcBaseListener {
	/** Map storing the val attribute for all parse tree nodes. */
	private ParseTreeProperty<Integer> vals;

	/** Initialises the calculator before using it to walk a tree. */
	public void init() {
		this.vals = new ParseTreeProperty<>();
	}

	@Override
	public void exitPar(ParContext ctx) {
		set(ctx, val(ctx.expr()));
	}

	@Override
	public void exitNumber(NumberContext ctx) {
		set(ctx, Integer.parseInt(ctx.NUMBER().getText()));
	}

	@Override
	public void exitTimes(TimesContext ctx) {
		set(ctx, val(ctx.expr(0)) * val(ctx.expr(1)));
	}

	@Override
	public void exitPlus(PlusContext ctx) {
		set(ctx, val(ctx.expr(0)) + val(ctx.expr(1)));
	}

    @Override
    public void exitNeg(CalcParser.NegContext ctx) {
        set(ctx, val(ctx.expr()) * -1);
    }

    /** Sets the val attribute of a given node. */
	private void set(ParseTree node, int val) {
		this.vals.put(node, val);
	}

	/** Retrieves the val attribute of a given node. */
	public int val(ParseTree node) {
		return this.vals.get(node);
	}
}
