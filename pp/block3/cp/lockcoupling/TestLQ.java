package block3.cp.lockcoupling;

import nl.utwente.pp.cp.junit.ConcurrentRunner;
import nl.utwente.pp.cp.junit.ThreadNumber;
import nl.utwente.pp.cp.junit.Threaded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;

@RunWith(ConcurrentRunner.class)
public class TestLQ {

    private final static int AMOUNT_OF_THREADS = 200;
    private final static int AMOUNT_OF_CALLS = 50;
    private static LQLinkedList lq0;
    private static LQLinkedList lq1;
    private static LQLinkedList lq2;
    private static LQLinkedList lq3;


    @Before
    public void before() {
        lq0 = new LQLinkedList();

        lq1 = new LQLinkedList();

        lq2 = new LQLinkedList();
        for (int t = 0; t < AMOUNT_OF_THREADS; t++) {
            for (int c = 0; c < AMOUNT_OF_CALLS; c++) {
                lq2.add(c);
            }
        }

        lq3 = new LQLinkedList();
        for (int i = 0; i < AMOUNT_OF_CALLS * AMOUNT_OF_THREADS; i++) {
            lq3.add(i);
        }
    }

    @Test
    @Threaded(count = AMOUNT_OF_THREADS)
    public void testAdd(@ThreadNumber int number) {
        for (int i = 0; i < AMOUNT_OF_CALLS; i++) {
            lq0.add(number);
        }
    }

//    @Test
//    @Threaded(count = AMOUNT_OF_THREADS)
//    public void testInsert(@ThreadNumber int number) {
//        Random r = new Random();
//        for (int i = 0; i < AMOUNT_OF_CALLS; i++) {
//            if (number % 2 == 0) {
//                lq1.insert(0, number);
//            } else {
//                lq1.insert(r.nextInt((AMOUNT_OF_CALLS/2)), number);
//            }
//        }
//    }

//    @Test
//    @Threaded(count = AMOUNT_OF_THREADS)
//    public void testRemove() {
//        for(int i = 0; i < AMOUNT_OF_CALLS; i++) {
//            lq2.remove(i);
//        }
//    }

//    @Test
//    @Threaded(count = AMOUNT_OF_THREADS)
//    public void testDelete(@ThreadNumber int number) {
//        Random r = new Random();
//        for(int i = 0; i < AMOUNT_OF_CALLS; i++) {
//            if (number % 2 == 0) {
//                lq3.delete(0);
//            } else {
//                lq3.delete(r.nextInt(AMOUNT_OF_CALLS/2));
//            }
//        }
//    }

    @After
    public void after() {
        assertEquals(AMOUNT_OF_THREADS*AMOUNT_OF_CALLS, lq0.size()); // ADD
//        assertEquals(AMOUNT_OF_THREADS*AMOUNT_OF_CALLS, lq1.size()); // INSERT
//        assertEquals(0, lq2.size());                                 // REMOVE
//        assertEquals(0, lq3.size());                                 // DELETE
    }

}
