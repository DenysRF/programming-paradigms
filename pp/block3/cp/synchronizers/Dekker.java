package block3.cp.synchronizers;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Dekker implements BasicLock {

    private volatile int turn;
    private AtomicIntegerArray flag = new AtomicIntegerArray(2);

    public Dekker() {
        flag.set(0, 0);
        flag.set(1, 0);
        turn = 0;
    }

    /**
     * Acquires the lock.
     *
     * @param threadNumber is the number of the requesting thread,
     *                     threadNumber == 0|1
     */
    @Override
    public void lock(int threadNumber) {
        int other = 1-threadNumber;
        flag.set(threadNumber, 1);
        while(flag.get(other) == 1) {
            if (turn == other) {
                flag.set(threadNumber, 0);
                while (turn == other);
                flag.set(threadNumber, 1);
            }
        }
    }

    /**
     * Releases the lock.
     *
     * @param threadNumber is the number of the releasing thread,
     *                     threadNumber == 0|1
     */
    @Override
    public void unlock(int threadNumber) {
        turn = 1-threadNumber;
        flag.set(threadNumber, 0);
    }

}
