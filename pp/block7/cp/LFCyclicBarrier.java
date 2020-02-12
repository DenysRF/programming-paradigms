package block7.cp;

import java.util.concurrent.atomic.AtomicInteger;

public class LFCyclicBarrier implements Barrier {

    private final int initialParties;
    private AtomicInteger parties;
    private volatile Boolean released;
    private AtomicInteger k;

    LFCyclicBarrier(int parties) {
        this.initialParties = parties;
        this.parties = new AtomicInteger();
        this.parties.set(parties);
        this.released = false;
        this.k = new AtomicInteger(parties);
    }

    @Override
    public int await() {
        parties.decrementAndGet();
        // if the last one reaches await
        // reset parties to initial and release
        if (parties.get() == 0) {
            this.parties.set(initialParties);
            this.released = true;
        }

        // if not released then spin
        while (!released);

        // after getting released decrement k
        k.decrementAndGet();
        // if k is zero, all parties have been released and the barrier can be reused
        if (k.get() == 0) {
            this.released = false;
            this.k.set(initialParties);
        }
        return parties.get();
    }
}
