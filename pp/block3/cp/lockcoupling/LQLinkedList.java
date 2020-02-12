package block3.cp.lockcoupling;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LQLinkedList implements List {

    private Node head;
    private AtomicInteger size;

    public LQLinkedList() {
        size = new AtomicInteger();
    }

    private class Node {
        private final Object value;
        private Node next;
        private final ReentrantLock lock;

        private Node(Object value) {
            this.value = value;
            this.lock = new ReentrantLock();
        }

        private void lock() {
            this.lock.lock();
        }

        private void unlock() {
            this.lock.unlock();
        }
    }


    /**
     * Insert an element at the specified position in the list.
     *
     * @param position The position to insert the element at.
     * @param value    The value of the element to insert.
     */
    @Override
    public void insert(int position, Object value) {
        if (position >= this.size()) { // insert at end
            add(value);
        } else if (position == 0) { // insert at front
            synchronized (this) {
                this.head.lock();
                try {
                    Node oldHead = this.head;
                    Node newNode = new Node(value);
                    newNode.lock();
                    try {
                        this.head = newNode;
                        newNode.next = oldHead;
                        this.size.incrementAndGet();
                    } finally {
                        newNode.unlock();
                    }
                } finally {
                    this.head.next.unlock();
                }
            }
        } else { // insert in between
            int i = 1;
            Node prev = this.head;
            prev.lock();
            try {
                prev.next.lock();
                Node current = prev.next;
                try {
                    while (i != position) {
                        current.next.lock();
                        ;
                        Node n = prev.next;
                        prev.unlock();
                        current = current.next;
                        prev = n;
                        i++;
                    }
                    Node newNode = new Node(value);
                    prev.next = newNode;
                    newNode.next = current;
                    this.size.incrementAndGet();
                } finally {
                    current.unlock();
                }
            } finally {
                prev.unlock();
            }
        }
    }

    /**
     * Add an element to the end of the list.
     *
     * @param value The value of the element to add to the list.
     */
    @Override
    public void add(Object value) {
        // if list is entirely empty
        synchronized (this) {
            if (this.size() == 0) {
                this.head = new Node(value);
                this.size.incrementAndGet();
                return;
            }
        }

        Node prev = this.head;
        prev.lock();
        try {
            if (prev.next == null) {
                prev.next = new Node(value);
                this.size.incrementAndGet();
            } else {
                Node current = prev.next;
                current.lock();
                try {
                    while (current.next != null) {
                        current.next.lock();
                        Node n = prev.next;
                        prev.unlock();
                        current = current.next;
                        prev = n;
                    }
                    current.next = new Node(value);
                    this.size.incrementAndGet();
                } finally {
                    current.unlock();
                }
            }
        } finally {
            prev.unlock();
        }

    }

    /**
     * Remove the specified element from the list.
     *
     * @param item The element to remove from the list.
     */
    @Override
    public void remove(Object item) {
        if (this.size() == 0) {
            return;
        }

        synchronized (this) {
//            this.head.lock();
//            try {
            if (this.head.value.equals(item)) { // remove first item
                if (this.head.next == null) { // head becomes null
                    this.head = null;
                    this.size.decrementAndGet();
                    return;
                } else { // head becomes next
                    this.head.next.lock();
                    try {
                        this.head = this.head.next;
                        this.size.decrementAndGet();
                    } finally {
                        this.head.unlock();
                    }
                    return;
                }
            }
//            } finally {
//                if (this.head != null) {
//                    this.head.unlock();
//                }
//            }
        }

        Node prev = this.head;
        prev.lock();
        try {
            if (prev.next != null) {
                Node current = prev.next;
                current.lock();
                try {
                    while (!current.value.equals(item)) {
                        if (current.next == null) {
                            return;
                        }
                        current.next.lock();
                        Node n = prev.next;
                        prev.unlock();
                        current = current.next;
                        prev = n;
                    }
                    prev.next = current.next;
                    this.size.decrementAndGet();
                } finally {
                    current.unlock();
                }
            }
        } finally {
            prev.unlock();
        }
    }

    /**
     * Delete the element at the specified position.
     *
     * @param position The position of the element that should be deleted.
     */
    @Override
    public void delete(int position) {
        if (position == 0) {
            synchronized (this) {
                if (this.head.next == null) {
                    this.head = null;
                    this.size.decrementAndGet();
                } else {
                    this.head = this.head.next;
                    this.size.decrementAndGet();
                }
            }
        } else {
            int i = 1;
            Node prev = this.head;
            prev.lock();
            try {
                if (prev.next != null) {
                    Node current = prev.next;
                    current.lock();
                    try {
                        while (i < position) {
                            if (current.next == null) {
                                prev.next = null;
                                this.size.decrementAndGet();
                                return;
                            }
                            current.next.lock();
                            Node n = prev.next;
                            prev.unlock();
                            current = current.next;
                            prev = n;
                            i++;
                        }
                        prev.next = current.next;
                        this.size.decrementAndGet();
                    } finally {
                        current.unlock();
                    }
                }
            } finally {
                prev.unlock();
            }
        }
    }

    /**
     * Get the amount of elements currently in this list.
     *
     * @return The size of the list.
     */
    @Override
    public int size() {
        return this.size.get();
    }

//    public ArrayList<Object> getList() {
//        ArrayList<Object> l = new ArrayList<>();
//        Node current = head;
//        l.add(head.value);
//        StringBuilder sb = new StringBuilder();
//        sb.append(head.value.toString());
//        while (current.next != null) {
//            l.add(current.next.value);
//            sb.append(" -> ");
//            sb.append(current.next.value.toString());
//            current = current.next;
//        }
//        System.out.println(sb.toString());
//        return l;
//    }
}
