package block3.cp.synchronizers;

import net.jcip.annotations.NotThreadSafe;
import nl.utwente.pp.cp.junit.ConcurrentRunner;
import nl.utwente.pp.cp.junit.ThreadNumber;
import nl.utwente.pp.cp.junit.Threaded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentRunner.class)
public class TestLock {

    private static final int AMOUNT_OF_THREADS = 2;
    private static final int AMOUNT_OF_CALLS = 100000;

    private UnsafeSequence us;
    private List<List<Integer>> allSequences;
    //private Dekker lock;
    private CompareAndSet lock;
    //private BasicReentrantLock lock;

    @NotThreadSafe
    private class UnsafeSequence {
        private int value;
        /** Returns a unique value. */
        public int getNext() {
            return value++;
        }
    }

    @Before
    public void before() {
        //lock = new Dekker();
        lock = new CompareAndSet();
        //lock = new BasicReentrantLock();
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
            lock.lock(number);
            //lock.lock(number); // is Reentrant
            try {
                allSequences.get(number).add(us.getNext());
            } finally {
                lock.unlock(number);
            }

        }
    }

    @After
    public void after() {
        // Check if no number is read more than once
        Set<Integer> s = new HashSet<>();
        for (List l : allSequences) {
            /** Print to show interleaving */
            System.out.println(l.toString());
            s.addAll(l);
        }
        assertEquals(AMOUNT_OF_THREADS * AMOUNT_OF_CALLS, s.size());
    }

}
