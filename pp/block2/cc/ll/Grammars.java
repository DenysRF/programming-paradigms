/**
 * 
 */
package block2.cc.ll;

import block2.cc.NonTerm;
import block2.cc.Symbol;
import block2.cc.SymbolFactory;
import block2.cc.Term;

/**
 * Class containing some example grammars.
 * @author Arend Rensink
 *
 */
public class Grammars {
	/** Returns a grammar for simple English sentences. */
	public static Grammar makeSentence() {
		// Define the non-terminals
		NonTerm sent = new NonTerm("Sentence");
		NonTerm subj = new NonTerm("Subject");
		NonTerm obj = new NonTerm("Object");
		NonTerm mod = new NonTerm("Modifier");
		// Define the terminals, using the Sentence.g4 lexer grammar
		// Make sure you take the token constants from the right class!
		SymbolFactory fact = new SymbolFactory(Sentence.class);
		Term noun = fact.getTerminal(Sentence.NOUN);
		Term verb = fact.getTerminal(Sentence.VERB);
		Term adj = fact.getTerminal(Sentence.ADJECTIVE);
		Term end = fact.getTerminal(Sentence.ENDMARK);
		// Build the context free grammar
		Grammar g = new Grammar(sent);
		g.addRule(sent, subj, verb, obj, end);
		g.addRule(subj, noun);
		g.addRule(subj, mod, subj);
		g.addRule(obj, noun);
		g.addRule(obj, mod, obj);
		g.addRule(mod, adj);
		return g;
	}

    public static Grammar makeIf() {
        // Define the non-terminals
        NonTerm stat = new NonTerm("Stat");
        NonTerm elsePart = new NonTerm("ElsePart");
        // Define the terminals, using the If.g4 lexer grammar
        // Make sure you take the token constants from the right class!
        SymbolFactory fact = new SymbolFactory(If.class);
        Term assT = fact.getTerminal(If.ASSIGN);
        Term ifT = fact.getTerminal(If.IF);
        Term exprT = fact.getTerminal(If.COND);
        Term thenT = fact.getTerminal(If.THEN);
        Term elseT = fact.getTerminal(If.ELSE);
        // Build the context free grammar
        Grammar g = new Grammar(stat);
        g.addRule(stat, assT);
        g.addRule(stat, ifT, exprT, thenT, stat, elsePart);
        g.addRule(elsePart, elseT, stat);
        g.addRule(elsePart);
        return g;
    }

    public static Grammar makeAbc() {
        // Define the non-terminals
        NonTerm l = new NonTerm("L");
        NonTerm r = new NonTerm("R");
        NonTerm rp = new NonTerm("Rp");
        NonTerm q = new NonTerm("Q");
        NonTerm qp = new NonTerm("Qp");
        // Define the terminals, using the Abc.g4 lexer grammar
        // Make sure you take the token constants from the right class!
        SymbolFactory fact = new SymbolFactory(Abc.class);
        Term a = fact.getTerminal(Abc.A);
        Term b = fact.getTerminal(Abc.B);
        Term c = fact.getTerminal(Abc.C);
        // Build the context free grammar
        Grammar g = new Grammar(l);
        g.addRule(l, r, a);
        g.addRule(l, q, b, a);
        g.addRule(r, a, b, a, rp);
        g.addRule(r, c, a, b, a, rp);
        g.addRule(rp, b, c, rp);
        g.addRule(rp);
        g.addRule(q, b, qp);
        g.addRule(qp, b, c);
        g.addRule(qp, c);
        return g;
    }

}
