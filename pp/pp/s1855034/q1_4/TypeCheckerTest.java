package pp.s1855034.q1_4;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeCheckerTest {
    private final ParseTreeWalker walker = new ParseTreeWalker();
    private final TypeChecker check = new TypeChecker();

    @Test
    public void testType() {
        test("LIST", 1, "[a]");
        test("ERR", "1 :: 1");
        test("ERR", "1 + 1");
        test("LIST", 2, "[]::[]");
        test("LETTER", "a");
        test("LIST", 2, "[a]::[b]");
        test("LIST", 2, "[[]]");

        /* Examples */
        test("LIST", 1, "[1, 2]");
        test("LIST", 2, "[1, [2], 3]");
        test("LIST", 3, "[1, [] :: []]");
        test("ERR", "[] + 2");
        test("ERR", "[3] + 4");
    }


    private void test(String type, int depth, String listExp) {
        ParseTree tree = parseListExp(listExp);
        this.check.init();
        this.walker.walk(this.check, tree);
        TypeChecker.Type t = this.check.type(tree);
        assertEquals(type, t.getType());
        assertEquals(depth, t.getDepth());
        System.err.println(check.getErrors());
    }

    private void test(String type, String listExp) {
        ParseTree tree = parseListExp(listExp);
        this.check.init();
        this.walker.walk(this.check, tree);
        TypeChecker.Type t = this.check.type(tree);
        assertEquals(type, t.getType());
        System.err.println(check.getErrors());
    }

    private ParseTree parseListExp(String text) {
        CharStream chars = CharStreams.fromString(text);
        Lexer lexer = new ListExpLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        ListExpParser parser = new ListExpParser(tokens);

        return parser.listExp();
    }
}
