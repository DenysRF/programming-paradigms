package block3.cc.test;

import block3.cc.antlr.*;
import block3.cc.antlr.TypeCalcAttrParser.ExprContext;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeCalcTest {
    private final ParseTreeWalker walker = new ParseTreeWalker();
    private final TypeCalculator calc = new TypeCalculator();

    @Test
    public void testHat() {
        test(Type.NUM, "123 ^ 456");
        test(Type.ERR, "true ^ 123");
        test(Type.STR, "abc ^ 123");
        test(Type.ERR, "123 ^ true");
        test(Type.ERR, "true ^ false");
        test(Type.ERR, "abc ^ false");
        test(Type.ERR, "456 ^ abc");
        test(Type.ERR, "true ^ def");
        test(Type.ERR, "xyz ^ ghi");
    }

    @Test
    public void testPlus() {
        test(Type.NUM, "123 + 456");
        test(Type.ERR, "true + 123");
        test(Type.ERR, "abc + 123");
        test(Type.ERR, "123 + true");
        test(Type.BOOL, "true + false");
        test(Type.ERR, "abc + false");
        test(Type.ERR, "456 + abc");
        test(Type.ERR, "true + def");
        test(Type.STR, "xyz + ghi");
    }

    @Test
    public void testEquals() {
        test(Type.BOOL, "123 = 456");
        test(Type.ERR, "true = 123");
        test(Type.ERR, "abc = 123");
        test(Type.ERR, "123 = true");
        test(Type.BOOL, "true = false");
        test(Type.ERR, "abc = false");
        test(Type.ERR, "456 = abc");
        test(Type.ERR, "true = def");
        test(Type.BOOL, "xyz = ghi");
    }

    @Test
    public void testCompound() {
        test(Type.BOOL, "(123 = 456) = false");
        test(Type.STR, "abc + abc ^ 132 + abc");
    }

    private void test(Type expected, String expr) {
        assertEquals(expected, parseTypeCalcAttr(expr).type);
        ParseTree tree = parseTypeCalc(expr);
        this.calc.init();
        this.walker.walk(this.calc, tree);
        assertEquals(expected, this.calc.type(tree));
    }

    private ParseTree parseTypeCalc(String text) {
        CharStream chars = CharStreams.fromString(text);
        Lexer lexer = new TypeCalcLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        TypeCalcParser parser = new TypeCalcParser(tokens);
        return parser.expr();
    }

    private ExprContext parseTypeCalcAttr(String text) {
        CharStream chars = CharStreams.fromString(text);
        Lexer lexer = new TypeCalcAttrLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        TypeCalcAttrParser parser = new TypeCalcAttrParser(tokens);
        return parser.expr();
    }

}
