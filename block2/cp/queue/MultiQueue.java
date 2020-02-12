package block2.cp.queue;

import java.util.ArrayList;

public class MultiQueue { //implements Queue {

    private final ArrayList<LLQueue> qs;

    MultiQueue(int n) {
        qs = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            qs.add(new LLQueue());
        }
    }

    /**
     * Pushes an element at the head of the queue.
     *
     * @param x
     */
    //@Override
    void push(int i, Object x) {
        qs.get(i).push(x);
    }

    /**
     * Obtains and removes the tail of the queue.
     */
    //@Override
    Object pull() throws QueueEmptyException {
        for (LLQueue q : qs) {
            synchronized (q) {
                if (q.getLength() > 0) {
                    return q.pull();
                }
            }
        }
        throw new QueueEmptyException();
    }

    /**
     * Returns the number of elements in the queue.
     */
    //@Override
    public int getLength() {
        int i = 0;
        for (LLQueue q : qs) {
            i += q.getLength();
        }
        return i;
    }
}
