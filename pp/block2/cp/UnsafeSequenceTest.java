package block2.cp;

import nl.utwente.pp.cp.junit.ConcurrentRunner;
import nl.utwente.pp.cp.junit.ThreadNumber;
import nl.utwente.pp.cp.junit.Threaded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentRunner.class)
public class UnsafeSequenceTest {

    private static final int AMOUNT_OF_THREADS = 10;
    private static final int AMOUNT_OF_CALLS = 100;

    private UnsafeSequence us;

    private List<List<Integer>> allSequences;

    @Before
    public void before() {
        us = new UnsafeSequence();
        allSequences = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            allSequences.add(new ArrayList<>());
        }
    }

    @Test
    @Threaded(count = AMOUNT_OF_THREADS)
    public void test(@ThreadNumber int number) {
        for (int i = 0; i < AMOUNT_OF_CALLS; i++) {
            allSequences.get(number).add(us.getNext());
        }
    }

    @After
    public void after() {
        // Check if no number is read more than once
        Set<Integer> s = new HashSet<>();
        for (List l : allSequences) {
            /** Print to show interleaving */
            //System.out.println(l.toString());
            s.addAll(l);
        }
        assertEquals(AMOUNT_OF_THREADS * AMOUNT_OF_CALLS, s.size());
    }
}
