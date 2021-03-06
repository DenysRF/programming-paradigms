package block3.cp.synchronizers;

public interface BasicLock {
    /**
     * Acquires the lock.
     * @param threadNumber is the number of the requesting thread,
     * threadNumber == 0|1
     */
    void lock(int threadNumber);
    /**
     * Releases the lock.
     * @param threadNumber is the number of the releasing thread,
     * threadNumber == 0|1
     */
    void unlock(int threadNumber);
}

