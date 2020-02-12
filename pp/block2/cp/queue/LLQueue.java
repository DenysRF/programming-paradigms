package block2.cp.queue;

import java.util.concurrent.atomic.AtomicInteger;

public class LLQueue implements Queue {

    private Node head;
    private AtomicInteger size = new AtomicInteger();

//    private Object lock1 = new Object();
//    private Object lock2 = new Object();

    public LLQueue() {
        this.head = null;
        size.set(0);
    }

    private class Node {
        Object content;
        Node next;

        Node(Object content) {
            this.content = content;
        }
    }

    /**
     * Pushes an element at the head of the queue.
     *
     * @param x
     */
    @Override
    public synchronized void push(Object x) {
        Node current = new Node(x);
        current.next = head;
        head = current;
        size.incrementAndGet();
    }

    /**
     * Obtains and removes the tail of the queue.
     */
    @Override
    public synchronized Object pull() throws QueueEmptyException {
        Node n = head;
        Object res;

        if (getLength() == 0) {
            throw new QueueEmptyException();
        } else if (getLength() == 1) {
            res = n.content;
            head = null;
            size.decrementAndGet();
        } else {
            while (n.next.next != null) {
                n = n.next;
            }
            res = n.next.content;
            n.next = null;
            size.decrementAndGet();
        }
        return res;
    }

    /**
     * Returns the number of elements in the queue.
     */
    @Override
    public int getLength() {
        return size.get();
    }
}
