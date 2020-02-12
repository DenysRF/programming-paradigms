package block3.cp.synchronizers;

import java.util.concurrent.locks.ReentrantLock;

public class BasicReentrantLock implements BasicLock {

    private ReentrantLock l0;
    private ReentrantLock l1;

    public BasicReentrantLock() {
        l0 = new ReentrantLock();
        l1 = new ReentrantLock();
    }

    /**
     * Acquires the lock.
     *
     * @param threadNumber is the number of the requesting thread,
     *                     threadNumber == 0|1
     */
    @Override
    public synchronized void lock(int threadNumber) {
        if (threadNumber == 0) {
            l0.lock();
        } else {
            l1.lock();
        }
    }

    /**
     * Releases the lock.
     *
     * @param threadNumber is the number of the releasing thread,
     *                     threadNumber == 0|1
     */
    @Override
    public synchronized void unlock(int threadNumber) {
        if ((threadNumber == 0)) {
            l0.unlock();
        } else {
            l1.unlock();
        }
    }
}
