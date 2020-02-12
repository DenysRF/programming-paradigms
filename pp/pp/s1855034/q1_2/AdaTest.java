package pp.s1855034.q1_2;

import org.junit.Test;

public class AdaTest {
    private static LexerTester tester = new LexerTester(Ada.class);

    @Test
    public void testCorrectDecimalLiterals() {
        tester.correct("00000001_2_3_4e+0_999");

        /* Examples from teh reference manual */
        tester.correct("12");
        tester.correct("0");
        tester.correct("1E6");
        tester.correct("123_456");
    }

    @Test
    public void testIncorrectDecimalLiterals() {
        tester.wrong("123_");
        tester.wrong("_123");
        tester.wrong("1__23");
        tester.wrong("123E");

    }

    @Test
    public void testCorrectBasedLiterals() {
        tester.correct("0_10#aA_bB_0123456789cDeF#e+0_1");

        /* Examples from teh reference manual */
        tester.correct("2#1111_1111#");
        tester.correct("16#FF#");
        tester.correct("016#0ff#");
        tester.correct("16#E#E1");
        tester.correct("2#1110_0000#");
    }

    @Test
    public void testIncorrectBasedLiterals() {
        tester.wrong("0#0#");
        tester.wrong("1#0#");
        tester.wrong("17#0#");
        tester.wrong("10##");
        tester.wrong("10#123#e+FF");

    }
}
