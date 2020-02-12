package project.tests;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import project.antlr.LanguageLexer;
import project.antlr.LanguageParser;
import project.antlr.TreeListener;

import java.io.IOException;

import static junit.framework.TestCase.*;

/**
 * Test class that tests multiple files containing programs if the syntax is correct
 * or when wrong if we produce the correct error.
 * @author denys, yernar
 */
public class TestSyntax {
    private ParseTreeWalker walker;
    private TreeListener listener;
    private final String path = "project/tests/testset/syntax/";

    /**
     * Initializes the TreeListener and ParseTreeWalker
     */
    @Before
    public void init() {
        listener = new TreeListener();
        walker = new ParseTreeWalker();
    }

    /**
     * asserts that BasicTypes.dy gives no syntax errors.
     * @throws IOException because it reads from a file
     */
    @Test
    public void testBasicTypes() throws IOException {
        assertEquals(0, parse(path + "BasicTypes.dy"));
    }

    /**
     * asserts that SimpleOperations.dy gives no syntax errors.
     * @throws IOException because it reads from a file
     */
    @Test
    public void testSimpleOperations() throws IOException {
        assertEquals(0, parse(path + "SimpleOperations.dy"));
    }

    /**
     * asserts that ComplexOperations.dy gives no syntax errors.
     * @throws IOException because it reads from a file
     */
    @Test
    public void testComplexOperations() throws IOException {
        assertEquals(0, parse(path + "ComplexOperations.dy"));
    }

    /**
     * asserts that ConditionalLogic.dy gives no syntax errors.
     * @throws IOException because is reads from a file
     */
    @Test
    public void testConditionalLogic() throws IOException {
        assertEquals(0, parse(path + "ConditionalLogic.dy"));
    }

    /**
     * asserts that ForkJoin.dy gives no syntax errors.
     * @throws IOException because it reads from a file
     */
    @Test
    public void testForkJoin() throws IOException {
        assertEquals(0, parse(path + "ForkJoin.dy"));
    }

    /**
     * does multiple assertions on variable declarations with wrong syntax
     * that they do result in syntax errors.
     */
    @Test
    public void testBadDeclaration() {
        String[] input = new String[5];
        input[0] = "int a = 10;";   // use '<-' instead of '='
        input[1] = "int _b;";       // id's must start with a letter
        input[2] = "int abc-123;";  // id's may only contain letters, numbers and underscores
        input[3] = "int c <- ;";    // no value given
        input[4] = "int fork <- 11;"; // fork is a reserved keyword

        for (String s : input) {
            System.out.println("Test: " + s);
            LanguageParser parser = parseText(s);
            parser.program();
            assertTrue(parser.getNumberOfSyntaxErrors() > 0);

            // reset for next iteration
            this.init();
        }
    }

    /**
     * does multiple assertions on if-else and while statements that have
     * wrong syntax and that they should produce syntax errors.
     */
    @Test
    public void testBadConditionalLogic(){
        String[] input = new String[2];
        input[0] = "if true\n" +            // no curly brackets used
                "       $do something\n " +
                "   else\n " +
                "       $ do nothing";
        input[1] = "while true {" +         // while cannot have an else
                "       $ do something\n " +
                "   } else {\n " +
                "       $ do nothing\n " +
                "   }";

        for (String s : input) {
            System.out.println("Test: " + s);
            LanguageParser parser = parseText(s);
            ParseTree tree = parser.program();
            walker.walk(listener, tree);
            assertTrue(parser.getNumberOfSyntaxErrors() > 0);

            // reset for next iteration
            this.init();
        }
    }

    /**
     * does multiple assertions on operations with wrong syntax
     * they should produce syntax errors.
     */
    @Test
    public void testBadOperations() {
        String[] input = new String[3];

        input[0] = "int a <- -1 - --1;";    // 1 too many '-'
        input[1] = "bool b <- 5 == 5;";     // equality operator should be single '='
        input[2] = "int d <- -(-1);";       // negation not part of our language

        for (String s : input) {
            System.out.println("Test: " + s);
            LanguageParser parser = parseText(s);
            ParseTree tree = parser.program();
            walker.walk(listener, tree);
            assertTrue(parser.getNumberOfSyntaxErrors() > 0);

            // reset for next iteration
            this.init();
        }
    }

    /**
     * Test whether the generated tree of the program within this method
     * if of the expected shape.
     */
    @Test
    public void testTree() {
        String input = "int a <- 1 + 1;";
        CharStream chars = CharStreams.fromString(input);
        Lexer lexer = new LanguageLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        ParseTree tree = parser.program();
        assertEquals(2, tree.getChildCount());
        assertEquals(5, tree.getChild(0).getChildCount());
        assertEquals(0, tree.getChild(1).getChildCount());
        assertEquals(3, tree.getChild(0).getChild(3).getChildCount());
    }

    /**
     * Creates and returns a LanguageParser for the given input
     * @param input the input program as a String
     * @return LanguageParser generated from the given input.
     */
    private LanguageParser parseText(String input) {
        CharStream chars = CharStreams.fromString(input);
        Lexer lexer = new LanguageLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        return new LanguageParser(tokens);
    }

    /**
     * Creates a parser for the given input which comes from a file
     * and parses it returning the amount of syntax errors that resulted
     * from parsing.
     * @param path the path and file name of the file that contains the program.
     * @return the amount of syntax errors as a result of parsing.
     * @throws IOException because the method reads from a file.
     */
    private int parse(String path) throws IOException {
        CharStream chars = CharStreams.fromFileName(path);
        Lexer lexer = new LanguageLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        ParseTree tree = parser.program();
        walker.walk(listener, tree);
        return parser.getNumberOfSyntaxErrors();
    }
}
