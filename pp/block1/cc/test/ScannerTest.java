package block1.cc.test;

import static block1.cc.dfa.State.ID6_DFA;
import static block1.cc.dfa.State.LALA_DFA;

import java.util.List;

import block1.cc.dfa.MyScanner;
import org.junit.Assert;
import org.junit.Test;

import block1.cc.dfa.Scanner;
import block1.cc.dfa.State;

/** Test class for Scanner implementation. */
public class ScannerTest {
	private Scanner myGen = new MyScanner();

	@Test
	public void testID6() {
		this.dfa = ID6_DFA;
		yields("");
		yields("a12345", "a12345");
		yields("a12345AaBbCc", "a12345", "AaBbCc");
	}

	@Test
    public void testLALA() {
	    this.dfa = LALA_DFA;
	    yields("");
	    yields("LaLa LaLaLaLi", "LaLa ", "LaLaLaLi");
	    yields("Laaaa LaLaa", "Laaaa La", "Laa");
    }

	/** Tests whether a given word can be scanned myGen.
	 * and results in the expected tokens.
	 * Throws an assertion error if the word is rejected or gives rise to 
	 * different tokens than those expected.
	 * @param word string to be scanned
	 * @param tokens sequence of tokens expected from scanning the word
	 */
	private void yields(String word, String... tokens) {
		List<String> result = this.myGen.scan(this.dfa, word);
		if (result == null) {
			Assert.fail(String.format(
					"Word '%s' is erroneously rejected by %s", word, this.dfa));
		}
		Assert.assertEquals(tokens.length, result.size());
		for (int i = 0; i < tokens.length; i++) {
			Assert.assertEquals(tokens[i], result.get(i));
		}
	}

	private State dfa;
}
