package block7.cp;

public class MyCyclicBarrier implements Barrier {

    private final int initialParties;
    private int parties;

    MyCyclicBarrier(int parties) {
        this.initialParties = parties;
        this.parties = parties;

    }

    @Override
    public synchronized int await() throws InterruptedException {
        parties--;

        if (parties > 0) {
            wait();
        } else {
            parties = initialParties;
            notifyAll();
        }
        return parties;
    }
}
