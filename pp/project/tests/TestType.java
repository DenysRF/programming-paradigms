package project.tests;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import project.antlr.LanguageLexer;
import project.antlr.LanguageParser;
import project.antlr.TreeListener;
import project.antlr.Type;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class that tests the type of various expressions and conditions.
 * @author denys, yernar
 */
public class TestType {
    private final ParseTreeWalker walker = new ParseTreeWalker();
    private final TreeListener listener = new TreeListener();

    /**
     * calls multiple tests for expressions that should be of type int.
     */
    @Test
    public void testInt() {
        testExpr(Type.INT, "0");
        testExpr(Type.INT, "1 + 1");
        testExpr(Type.INT, "-2 - -2");
        testExpr(Type.INT, "3 * 3");
        testExpr(Type.INT, "(4 + 4) * 4");
        testExpr(Type.INT, "-5 + 5 - -5 * 5");
        testExpr(Type.INT, "6");
        testExpr(Type.INT, "-7");
    }

    /**
     * calls multiple tests for expressions that should be of type bool.
     */
    @Test
    public void testBool() {
        testExpr(Type.BOOL, "true");
        testExpr(Type.BOOL, "(false)");
        testExpr(Type.BOOL, "true ");
        testExpr(Type.BOOL, "0 < 0");
        testExpr(Type.BOOL, "1 > 1");
        testExpr(Type.BOOL, "2 = -2");
        testExpr(Type.BOOL, "true = false");
        testExpr(Type.BOOL, "-3 not= 3");
        testExpr(Type.BOOL, "false not= false");
        testExpr(Type.BOOL, "4 <= 4");
        testExpr(Type.BOOL, "-5 >= -5");

        testExpr(Type.BOOL, "(5 - -1 * 3 = 18) = true");
        testExpr(Type.BOOL, "-1 - 1 not= -1 * 2");
        testExpr(Type.BOOL, "(2 + 2 > 3 + 3) = true");
    }

    /**
     * calls multiple tests for conditional statements of which the
     * condition should be of type bool.
     */
    @Test
    public void testConditional() {
        testStat(Type.BOOL, "if true {} else {}");
        testStat(Type.BOOL, "if (2 + 2 > 3 + 3) = true {}");

        testStat(Type.BOOL, "while true {" +
                                        "int x <- 0;" +
                                    "}");
        testStat(Type.BOOL, "while (2 + 2 > 3 + 3) = true {}");
    }

    /**
     * calls multiple tests on various expressions and statements
     * that should all result in type errors.
     */
    @Test
    public void testErr() {
        testExpr(Type.ERR, "1 + false");
        testExpr(Type.ERR, "false * true");
        testExpr(Type.ERR, "(false + 1) >= (true + 1)");
        testExpr(Type.ERR, "1+1 = true");

        testStat(Type.ERR, "if 0 {}");
        testStat(Type.ERR, "if -1--1--1*-1 {} else {}");
        testStat(Type.ERR, "while 0 {}");
        testStat(Type.ERR, "while -1--1--1*-1 {}");
    }

    /**
     * asserts that the type of the given expressions is the expected type.
     * @param expected the expected type.
     * @param input the given expression.
     */
    private void testExpr(Type expected, String input) {
        ParseTree tree = parseTree(input).expr();
        this.walker.walk(this.listener, tree);
        System.err.println("Test: " + input + "\n" + listener.getErrors());
        assertEquals(expected, this.listener.type(tree));
    }

    /**
     * asserts that the type within the given statement is the expected type.
     * @param expected the expected type
     * @param input the given statement.
     */
    private void testStat(Type expected, String input) {
        ParseTree tree = parseTree(input).stat();
        this.walker.walk(this.listener, tree);
        System.err.println("Test: " + input + "\n" + listener.getErrors());
        assertEquals(expected, this.listener.type(tree));
    }

    /**
     * Creates and returns a LanguageParser for the given input.
     * @param input the program code.
     * @return a LanguageParser generated form the input.
     */
    private LanguageParser parseTree(String input) {
        CharStream chars = CharStreams.fromString(input);
        Lexer lexer = new LanguageLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        return new LanguageParser(tokens);
    }
}
