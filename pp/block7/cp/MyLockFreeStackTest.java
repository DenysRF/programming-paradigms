package block7.cp;

import nl.utwente.pp.cp.junit.ConcurrentRunner;
import nl.utwente.pp.cp.junit.ThreadNumber;
import nl.utwente.pp.cp.junit.Threaded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(ConcurrentRunner.class)
public class MyLockFreeStackTest {
    private static final int AMOUNT_OF_THREADS = 100;
    private static final int AMOUNT_OF_CALLS = 50;

    private MyLockFreeStack s;

    @Before
    public void before() {
        s = new MyLockFreeStack();
    }

    @Test
    @Threaded(count = AMOUNT_OF_THREADS)
    public void test(@ThreadNumber int number) {
        for (int i = 0; i < AMOUNT_OF_CALLS; i++) {
            if (i < AMOUNT_OF_CALLS/2) {
                s.push(i);
                System.out.printf("Push: %d, Thread no %d\n", i, number);

                // uncomment else block to also test for popping
            } else {
                Object p = s.pop();
                System.out.printf("Pop: %d, Thread no %d\n", p, number);
            }
        }
    }

    @After
    public void after() {
        // for testing with popping
        assertEquals(0, s.getLength());

        // for testing without popping
        //assertEquals((AMOUNT_OF_CALLS*AMOUNT_OF_THREADS)/2, s.getLength());
    }
}
