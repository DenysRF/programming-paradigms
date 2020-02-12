package block3.cp.synchronizers;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSet implements BasicLock {

//    private final AtomicInteger turn = new AtomicInteger(-1);
    private final Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    /**
     * Acquires the lock.
     *
     * @param threadNumber is the number of the requesting thread,
     *                     threadNumber == 0|1
     */
    @Override
    public void lock(int threadNumber) {
        queue.add(threadNumber);
//        if (threadNumber != turn.get()) {
            while (/*!turn.compareAndSet(-1, threadNumber) && */queue.peek().equals(threadNumber));
//        }
    }

    /**
     * Releases the lock.
     *
     * @param threadNumber is the number of the releasing thread,
     *                     threadNumber == 0|1
     */
    @Override
    public void unlock(int threadNumber) {
        while (/*!turn.compareAndSet(threadNumber,-1) && */queue.peek().equals(threadNumber));
        queue.poll();
    }
}
