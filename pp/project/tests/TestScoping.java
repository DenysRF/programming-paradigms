package project.tests;
import org.junit.Test;

import project.antlr.ParseTreeGenerator;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class that tests multiple files containing programs if the scoping of variables is correct
 * or when wrong if we produce the correct error.
 * @author denys, yernar
 */
public class TestScoping {

    private final String PATH = "project/tests/testset/scoping/";

    @Test
    /**
     * Calls test for the test programs where we expect the scoping to be
     * correct.
     */
    public void testCorrect() throws IOException {
        String empty = "Errors:\n";
        test(empty, PATH + "DeclareSameScopeLevelButDifferentScope.dy");
        test(empty, PATH + "RedeclareInDeeperScope.dy");
        test(empty, PATH + "Fib0to20.dy");
    }

    @Test
    /**
     * Calls test for the test programs where we expect the scoping to be wrong.
     */
    public void testBad() throws IOException {
        String empty = "Errors:\n";
        String s1 = empty + addError(true, "b", 1, 6)
                + addError(true, "b", 2, 4)
                + addError(true, "b", 2, 9);
        String s2 = empty + addError(true,  "x", 1, 9);
        String s3 = empty + addError(false, "a", 8, 0);
        String s4 = empty + addError(false, "x", 2, 0);
        String s5 = empty + addError(true, "x", 13, 6);

        test(s1, PATH + "UseBeforeDeclare.dy");
        test(s2, PATH + "UseWithoutDeclare.dy");
        test(s3, PATH + "DoubleDeclare.dy");
        test(s4, PATH + "DifferentTypeSameID.dy");
        test(s5, PATH + "UseNotInScope.dy");
    }

    /**
     * Private helper method used to construct expected error messages that will
     * correspond to the actual error message.
     * @param use true if we expect the variable to not yet be declared in the scope
     *            when it is used and false if we expect the variable to ab already
     *            defined in the current scope when used.
     * @param id the name of the variable
     * @param line the line number where the error occurs in the program.
     * @param pos the character position in the line where the error occurred in the program.
     * @return a string containing the full error message.
     */
    private String addError(boolean use, String id, int line, int pos) {
        String badUse = ": id not yet defined in enclosing scope\n";
        String badDecl = ": id already defined in current scope\n";
        StringBuilder sb = new StringBuilder();
        String s = id + " at line " + line + ", position " + pos;
        sb.append(s);
        if (use) {
            sb.append(badUse);
        } else {
            sb.append(badDecl);
        }

        return sb.toString();
    }

    /**
     * Executes the test and does an assertion on the expected and actual error messages.
     * @param error the expected error message.
     * @param fileName the path and name of the file that is tested.
     * @throws IOException because this method leads to accessing a file.
     */
    private void test(String error, String fileName) throws IOException {
        ParseTreeGenerator gen = new ParseTreeGenerator(false);
        gen.start(fileName);
        System.err.println("Test: " + fileName.substring(PATH.length()) + "\n" + gen.getErrors());
        assertEquals(error, gen.getErrors());
    }

}
