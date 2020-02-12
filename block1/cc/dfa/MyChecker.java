package block1.cc.dfa;

public class MyChecker implements Checker {


    /**
     * Returns <code>true</code> if the DFA with the given start state accepts
     * the given word.
     *
     * @param start
     * @param word
     */
    @Override
    public boolean accepts(State start, String word) {
        int i = 0;
        while (i < word.length() && start.hasNext(word.charAt(i))) {
            start = start.getNext(word.charAt(i));
            i++;
        }
        return start.isAccepting() && i == word.length();
    }
}
