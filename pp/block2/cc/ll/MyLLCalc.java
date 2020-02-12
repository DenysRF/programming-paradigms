package block2.cc.ll;

import block2.cc.NonTerm;
import block2.cc.Symbol;
import block2.cc.Term;

import java.util.*;

public class MyLLCalc implements LLCalc {

    private Grammar g;

    public MyLLCalc (Grammar g) {
        this.g = g;
    }

    /**
     * Returns the FIRST-map for the grammar of this calculator instance.
     */
    @Override
    public Map<Symbol, Set<Term>> getFirst() {

        Map<Symbol, Set<Term>> firstMap = new HashMap<>();
        Map<Symbol, Set<Term>> compareMap = new HashMap<>();

        // Initialize Map as empty for every NT and as single T for every T
        for (NonTerm nt : g.getNonterminals()) {
            firstMap.put(nt, new HashSet<>());
        }
        for (Term t : g.getTerminals()) {
            firstMap.put(t, new HashSet<>(Collections.singleton(t)));
        }
        // Also for EOF and Epsilon
        firstMap.put(Symbol.EMPTY, new HashSet<>(Collections.singleton(Symbol.EMPTY)));
        firstMap.put(Symbol.EOF, new HashSet<>(Collections.singleton(Symbol.EOF)));

        while (!firstMap.equals(compareMap)) {
            // (Deep)Copy the firstMap to compare if it changed after an iteration
            compareMap.clear();
            for (Map.Entry<Symbol, Set<Term>> entry : firstMap.entrySet()) {
                compareMap.put(entry.getKey(), new HashSet<>(entry.getValue()));
            }
            for (Rule p : g.getRules()) {
                int i = 0;
                Set<Term> rhs = new HashSet<>();
                if (!p.getRHS().isEmpty()) {
                    rhs.addAll(firstMap.get(p.getRHS().get(0)));
                    rhs.remove(Symbol.EMPTY);
                    while (firstMap.get(p.getRHS().get(i)).contains(Symbol.EMPTY) &&
                            (i <= p.getRHS().size() - 1)) {
                        rhs.addAll(firstMap.get(p.getRHS().get(i+1)));
                        rhs.remove(Symbol.EMPTY);
                        i++;
                    }
                } else {
                    rhs.add(Symbol.EMPTY);
                }
                if (i == p.getRHS().size() - 1 &&
                        firstMap.get(p.getRHS().get(i)).isEmpty()) {
                    rhs.add(Symbol.EMPTY);
                }
                firstMap.get(p.getLHS()).addAll(rhs);
            }
        }
        return firstMap;
    }

    /**
     * Returns the FOLLOW-map for the grammar of this calculator instance.
     */
    @Override
    public Map<NonTerm, Set<Term>> getFollow() {
        Map<NonTerm, Set<Term>> followMap = new HashMap<>();
        Map<NonTerm, Set<Term>> compareMap = new HashMap<>();
        Set<Term> trailer = new HashSet<>();

        // Initialize followMap as empty for every NT
        for (NonTerm nt : g.getNonterminals()) {
            followMap.put(nt, new HashSet<>());
        }

        // First NT starts with EOF in its follow
        followMap.get(g.getRules().get(0).getLHS()).add(Symbol.EOF);

        while (!followMap.equals(compareMap)) {
            // (Deep)Copy the followMap to compare if it changed after an iteration
            compareMap.clear();
            for (Map.Entry<NonTerm, Set<Term>> entry : followMap.entrySet()) {
                compareMap.put(entry.getKey(), new HashSet<>(entry.getValue()));
            }
            for (Rule p : g.getRules()) {
                trailer.clear();
                trailer.addAll(followMap.get(p.getLHS()));

                for (int i = p.getRHS().size(); i >= 1; i--) {
                    Set<Term> first = getFirst().get(p.getRHS().get(i - 1));
                    if (p.getRHS().get(i - 1) instanceof NonTerm) {
                        followMap.get(p.getRHS().get(i - 1)).addAll(trailer);
                        if (first.contains(Symbol.EMPTY)) {
                            trailer.addAll(first);
                            trailer.remove(Symbol.EMPTY);
                        } else {
                            trailer.clear();
                            trailer.addAll(first);
                        }
                    } else {
                        trailer.clear();
                        trailer.addAll(first);
                    }
                }
            }
        }
        return followMap;
    }

    /**
     * Returns the FIRST+-map for the grammar of this calculator instance.
     */
    @Override
    public Map<Rule, Set<Term>> getFirstp() {
        Map<Rule, Set<Term>> firstpMap = new HashMap<>();

        Map<Symbol, Set<Term>> firstMap = getFirst();
        Map<NonTerm, Set<Term>> followMap = getFollow();

        for (Rule p : g.getRules()) {
            if (p.getRHS().isEmpty()) {
                firstpMap.put(p, new HashSet<>(Collections.singleton(Symbol.EMPTY)));
                firstpMap.get(p).addAll(followMap.get(p.getLHS()));
            } else {
                firstpMap.put(p, firstMap.get(p.getRHS().get(0)));
                if (firstMap.get(p.getRHS().get(0)).contains(Symbol.EMPTY)) {
                    firstpMap.get(p).addAll(followMap.get(p.getLHS()));
                }
            }
        }
        return firstpMap;
    }

    /**
     * Indicates if the grammar of this calculator instance is LL(1).
     */
    @Override
    public boolean isLL1() {
        Map<Rule, Set<Term>> firstpMap = getFirstp();
        for (NonTerm nt : g.getNonterminals()) {
            Set<Symbol> noOverlap = new HashSet<>();
            for (Rule p : g.getRules()) {
                if (p.getLHS().equals(nt)) {
                    for (Symbol s : firstpMap.get(p)) {
                        if (noOverlap.contains(s)) {
                            return false;
                        } else {
                            noOverlap.add(s);
                        }
                    }
                }
            }
        }
        return true;
    }
}
