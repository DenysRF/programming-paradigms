package block1.cc.test;

import org.junit.Test;

import block1.cc.antlr.PL1;

public class PL1Test {
    private static LexerTester tester = new LexerTester(PL1.class);

    @Test
    public void testCorrect() {
        tester.correct("\"wow\"");
        tester.correct("\"wow\"\"wow\"");
    }

    @Test
    public void testWrong() {
        tester.wrong("\"wow \" wow\"");
    }
}
