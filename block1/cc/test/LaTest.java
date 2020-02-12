package block1.cc.test;

import org.junit.Test;

import block1.cc.antlr.La;

public class LaTest {
    private static LexerTester tester = new LexerTester(La.class);

    @Test
    public void testCorrect() {
        tester.correct("La");
        tester.correct("Laaaa La");
        tester.correct("La La La Li   ");
    }

    @Test
    public void testWrong() {
        tester.wrong("La La La La Li");
    }
}
