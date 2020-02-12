package block2.cp.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CLQueue implements Queue {

    ConcurrentLinkedQueue<Object> q;

    CLQueue() {
        q = new ConcurrentLinkedQueue<>();
    }

    /**
     * Pushes an element at the head of the queue.
     *
     * @param x
     */
    @Override
    public void push(Object x) {
        q.add(x); // actually adds to the tail of q
    }

    /**
     * Obtains and removes the tail of the queue.
     */
    @Override
    public Object pull() throws QueueEmptyException {
        Object res = q.poll(); // actually removes the head of q
        if (res == null) {
            throw new QueueEmptyException();
        } else {
            return res;
        }

    }

    /**
     * Returns the number of elements in the queue.
     */
    @Override
    public int getLength() {
        return q.size();
    }
}
