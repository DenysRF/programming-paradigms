package block2.cp.queue;

import nl.utwente.pp.cp.junit.ConcurrentRunner;
import nl.utwente.pp.cp.junit.ThreadNumber;
import nl.utwente.pp.cp.junit.Threaded;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(ConcurrentRunner.class)
public class MultiQueueTest {

    private static final int AMOUNT_OF_THREADS = 100;
    private static final int AMOUNT_OF_CALLS = 1000;

    private final Random random = new Random();

    private MultiQueue queue;

    private long time;

    @Before
    public void before() {
        queue = new MultiQueue(AMOUNT_OF_THREADS);
        time = System.currentTimeMillis();
    }

    @Test
    @Threaded(count = AMOUNT_OF_THREADS )
    public void test(@ThreadNumber int threadNumber) throws InterruptedException, QueueEmptyException {
        producer(threadNumber);
        consumer(threadNumber);
    }

    @After
    public void after() {
        System.out.println("Test took: " + ((System.currentTimeMillis() - time) + " ms."));
        Assert.assertEquals(0, queue.getLength());
    }

    private void producer(int num) {
        for (int i = 0; i < AMOUNT_OF_CALLS; i++) {
            int write = random.nextInt(100);
            //System.out.printf("Producer %d: Pushed %d.%n", num, write);
            queue.push(num, write);
        }
    }

    private void consumer (int num) throws InterruptedException, QueueEmptyException {
        for (int i = AMOUNT_OF_CALLS; i > 0; i--) {
            Object read = queue.pull();
            if (read == null) {
                Thread.sleep(50);
            } else {
                //System.out.printf("Consumer %d: Pulled %d.%n", num, read);
            }
        }
    }
}
