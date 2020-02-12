package block7.cp;

import java.util.concurrent.atomic.AtomicReference;

public class MyLockFreeStack<T> implements LockFreeStackInterface {
    AtomicReference<Node<T>> head = new AtomicReference<Node<T>>();

    /**
     * 7-CP.1.3
     * Cannot implement bounded stack because cannot deal with
     * checking/tracking length of stack
     */

    @Override
    public void push(Object x) {
        Node<T> newHead = new Node<>((T) x);
        Node<T> oldHead;
        do {
            oldHead = head.get();
            newHead.next = oldHead;
        } while (!head.compareAndSet(oldHead, newHead));
    }


    @Override
    public Object pop() {
        Node<T> oldHead;
        Node<T> newHead;

        do {
            oldHead = head.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!head.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    @Override
    public int getLength() {
        int res = 0;
        Node<T> n = this.head.get();
        do {
            if (n == null) {
                return res;
            }
            n = n.next;
            res++;
        } while (n.next != null);
        res++;
        return res;
    }

    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node(T item) {
            this.item = item;
        }
    }

}
