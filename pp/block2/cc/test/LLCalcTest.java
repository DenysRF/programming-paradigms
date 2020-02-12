package block2.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import block2.cc.ll.*;
import org.junit.Test;

import block2.cc.NonTerm;
import block2.cc.Symbol;
import block2.cc.Term;

public class LLCalcTest {
	Grammar sentenceG = Grammars.makeSentence();
	// Define the non-terminals
	NonTerm subj = sentenceG.getNonterminal("Subject");
	NonTerm obj = sentenceG.getNonterminal("Object");
	NonTerm sent = sentenceG.getNonterminal("Sentence");
	NonTerm mod = sentenceG.getNonterminal("Modifier");
	// Define the terminals
	Term adj = sentenceG.getTerminal(Sentence.ADJECTIVE);
	Term noun = sentenceG.getTerminal(Sentence.NOUN);
	Term verb = sentenceG.getTerminal(Sentence.VERB);
	Term end = sentenceG.getTerminal(Sentence.ENDMARK);
	// Now add the last rule, causing the grammar to fail
	Grammar sentenceXG = Grammars.makeSentence();
	{    sentenceXG.addRule(mod, mod, mod);
	}
	LLCalc sentenceXLL = createCalc(sentenceXG);

	/** Tests the LL-calculator for the Sentence grammar. */
	@Test
	public void testSentenceOrigLL1() {
		// Without the last (recursive) rule, the grammar is LL-1
		assertTrue(createCalc(sentenceG).isLL1());
	}

	@Test
	public void testSentenceXFirst() {
		Map<Symbol, Set<Term>> first = sentenceXLL.getFirst();
		assertEquals(set(adj, noun), first.get(sent));
		assertEquals(set(adj, noun), first.get(subj));
		assertEquals(set(adj, noun), first.get(obj));
		assertEquals(set(adj), first.get(mod));
	}
	
	@Test
	public void testSentenceXFollow() {
		// FOLLOW sets
		Map<NonTerm, Set<Term>> follow = sentenceXLL.getFollow();
		assertEquals(set(Symbol.EOF), follow.get(sent));
		assertEquals(set(verb), follow.get(subj));
		assertEquals(set(end), follow.get(obj));
		assertEquals(set(noun, adj), follow.get(mod));
	}
	
	@Test
	public void testSentenceXFirstPlus() {
		// Test per rule
		Map<Rule, Set<Term>> firstp = sentenceXLL.getFirstp();
		List<Rule> subjRules = sentenceXG.getRules(subj);
		assertEquals(set(noun), firstp.get(subjRules.get(0)));
		assertEquals(set(adj), firstp.get(subjRules.get(1)));
	}
	
	@Test
	public void testSentenceXLL1() {
		assertFalse(sentenceXLL.isLL1());
	}

    Grammar statementG = Grammars.makeIf();
    // Define the non-terminals
    NonTerm stat = statementG.getNonterminal("Stat");
    NonTerm elsePart = statementG.getNonterminal("ElsePart");

    // Define the terminals
    Term assT = statementG.getTerminal(If.ASSIGN);
    Term ifT = statementG.getTerminal(If.IF);
    Term exprT = statementG.getTerminal(If.COND);
    Term thenT = statementG.getTerminal(If.THEN);
    Term elseT = statementG.getTerminal(If.ELSE);

    LLCalc statementLL = createCalc(statementG);

    @Test
    public void testStatementLLFirst() {
        Map<Symbol, Set<Term>> first = statementLL.getFirst();
        assertEquals(set(assT, ifT), first.get(stat));
        assertEquals(set(elseT, Symbol.EMPTY), first.get(elsePart));
    }

    @Test
    public void testStatementLLFollow() {
        // FOLLOW sets
        Map<NonTerm, Set<Term>> follow = statementLL.getFollow();
        assertEquals(set(elseT, Symbol.EOF), follow.get(stat));
        assertEquals(set(elseT, Symbol.EOF), follow.get(elsePart));
    }

    @Test
    public void testStatementLLFirstPlus() {
        // Test per rule
        Map<Rule, Set<Term>> firstp = statementLL.getFirstp();

        List<Rule> statRules = statementG.getRules(stat);
        assertEquals(set(assT), firstp.get(statRules.get(0)));
        assertEquals(set(ifT), firstp.get(statRules.get(1)));

        List<Rule> elsePartRules = statementG.getRules(elsePart);
        assertEquals(set(elseT), firstp.get(elsePartRules.get(0)));
        assertEquals(set(elseT, Symbol.EOF, Symbol.EMPTY), firstp.get(elsePartRules.get(1)));
    }

    @Test
    public void testStatementLL1() {
        assertFalse(statementLL.isLL1());
    }

    Grammar abcG = Grammars.makeAbc();
    // Define the non-terminals
    NonTerm l = abcG.getNonterminal("L");
    NonTerm r = abcG.getNonterminal("R");
    NonTerm rp = abcG.getNonterminal("Rp");
    NonTerm q = abcG.getNonterminal("Q");
    NonTerm qp = abcG.getNonterminal("Qp");

    // Define the terminals
    Term a = abcG.getTerminal(Abc.A);
    Term b = abcG.getTerminal(Abc.B);
    Term c = abcG.getTerminal(Abc.C);

    LLCalc abcLL = createCalc(abcG);

    @Test
    public void testAbcLLFirst() {
        Map<Symbol, Set<Term>> first = abcLL.getFirst();
        assertEquals(set(a, c, b), first.get(l));
        assertEquals(set(a, c), first.get(r));
        assertEquals(set(b, Symbol.EMPTY), first.get(rp));
        assertEquals(set(b), first.get(q));
        assertEquals(set(b, c), first.get(qp));
    }

    @Test
    public void testAbcLLFollow() {
        // FOLLOW sets
        Map<NonTerm, Set<Term>> follow = abcLL.getFollow();
        assertEquals(set(Symbol.EOF), follow.get(l));
        assertEquals(set(a), follow.get(r));
        assertEquals(set(a), follow.get(rp));
        assertEquals(set(b), follow.get(q));
        assertEquals(set(b), follow.get(qp));
    }

    @Test
    public void testAbcLLFirstPlus() {
        // Test per rule
        Map<Rule, Set<Term>> firstp = abcLL.getFirstp();

        List<Rule> lRules = abcG.getRules(l);
        assertEquals(set(a, c), firstp.get(lRules.get(0)));
        assertEquals(set(b), firstp.get(lRules.get(1)));

        List<Rule> rRules = abcG.getRules(r);
        assertEquals(set(a), firstp.get(rRules.get(0)));
        assertEquals(set(c), firstp.get(rRules.get(1)));

        List<Rule> rpRules = abcG.getRules(rp);
        assertEquals(set(b), firstp.get(rpRules.get(0)));
        assertEquals(set(a, Symbol.EMPTY), firstp.get(rpRules.get(1)));

        List<Rule> qRules = abcG.getRules(q);
        assertEquals(set(b), firstp.get(qRules.get(0)));

        List<Rule> qpRules = abcG.getRules(qp);
        assertEquals(set(b), firstp.get(qpRules.get(0)));
        assertEquals(set(c), firstp.get(qpRules.get(1)));
    }

    @Test
    public void testAbcLL1() {
        assertTrue(abcLL.isLL1());
    }

    /** Creates an LL1-calculator for a given grammar. */
    private LLCalc createCalc(Grammar g) {
        return new MyLLCalc(g);
    }

	@SuppressWarnings("unchecked")
	private <T> Set<T> set(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}
}
