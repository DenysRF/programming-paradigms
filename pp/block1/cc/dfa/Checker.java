package block1.cc.dfa;

/** Algorithm interface for checking whether a given DFA accepts a given word. */
public interface Checker {
	/**
	 * Returns <code>true</code> if the DFA with the given start state accepts
	 * the given word.
	 */
	boolean accepts(State start, String word);
}
