package block2.cc.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.math.BigInteger;

public class Calculator extends ArithmeticBaseListener {

    public static void main(String[] args) {
        String test1 = "2*--(--2*--3)";
        String test2 = "1-2+3*--2";
        String test3 = "((--2)^2) + 3*5+--6";
        System.out.println(new Calculator().calculate(test3));

    }

    public Calculator() {
        values = new ParseTreeProperty<>();
    }

    private ParseTreeProperty<BigInteger> values;

    public BigInteger calculate(String expr) {

        CharStream stream = CharStreams.fromString(expr);
        Lexer lexer = new ArithmeticLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);
        parser.addParseListener(this);
        ParseTree tree = parser.expr();

        return values.get(tree);
    }

    @Override
    public void exitPlus(ArithmeticParser.PlusContext ctx) {
        super.exitPlus(ctx);
        BigInteger left = values.get(ctx.getChild(0));
        BigInteger right = values.get(ctx.getChild(2));
        values.put(ctx, left.add(right));

    }

    @Override
    public void exitMinus(ArithmeticParser.MinusContext ctx) {
        super.exitMinus(ctx);
        BigInteger left = values.get(ctx.getChild(0));
        BigInteger right = values.get(ctx.getChild(2));
        values.put(ctx, left.subtract(right));
    }

    @Override
    public void exitNumber(ArithmeticParser.NumberContext ctx) {
        super.exitNumber(ctx);
        values.put(ctx, new BigInteger(ctx.getText()));
    }

    @Override
    public void exitMult(ArithmeticParser.MultContext ctx) {
        super.exitMult(ctx);
        BigInteger left = values.get(ctx.getChild(0));
        BigInteger right = values.get(ctx.getChild(2));
        values.put(ctx, left.multiply(right));
    }

    @Override
    public void exitNeg(ArithmeticParser.NegContext ctx) {
        super.exitNeg(ctx);
        BigInteger i = values.get(ctx.getChild(1));
        values.put(ctx, i.negate());
    }

    @Override
    public void exitExp(ArithmeticParser.ExpContext ctx) {
        super.exitExp(ctx);
        BigInteger i = values.get(ctx.getChild(0));
        BigInteger toThe = values.get(ctx.getChild(2));
        values.put(ctx, i.pow(toThe.intValue()));
    }

    @Override
    public void exitParentheses(ArithmeticParser.ParenthesesContext ctx) {
        super.exitParentheses(ctx);
        BigInteger i = values.get(ctx.getChild(1));
        values.put(ctx, i);
    }
}
