package block1.cc.test;

import static block1.cc.dfa.State.ID6_DFA;
import static block1.cc.dfa.State.LALA_DFA;

import block1.cc.dfa.MyChecker;
import org.junit.Assert;
import org.junit.Test;

import block1.cc.dfa.Checker;
import block1.cc.dfa.State;

/** Test class for Checker implementation. */
public class CheckerTest {
	private Checker myChecker = new MyChecker();

	private State dfa;

	@Test
	public void testID6() {
		this.dfa = ID6_DFA;
		accepts("a12345");
		rejects("");
		rejects("a12 45");
		rejects("a12 456");
		rejects("a123456");
		rejects("123456");
	}

	@Test
    public void testLALA() {
	    this.dfa = LALA_DFA;
	    accepts("La");
	    accepts("Laaaa La");
	    accepts("La La La Li   ");
	    rejects("");
	    rejects("La La La La Li");
    }


	private void accepts(String word) {
		if (!this.myChecker.accepts(this.dfa, word)) {
			Assert.fail(String.format(
					"Word '%s' is erroneously rejected by %s", word, this.dfa));
		}
	}

	private void rejects(String word) {
		if (this.myChecker.accepts(this.dfa, word)) {
			Assert.fail(String.format(
					"Word '%s' is erroneously accepted by %s", word, this.dfa));
		}
	}
}
