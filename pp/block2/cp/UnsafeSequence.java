package block2.cp;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/** Unedited */
@NotThreadSafe
public class UnsafeSequence {
    private int value;
    /** Returns a unique value. */
    public int getNext() {
        return value++;
    }
}

/** AtomicInteger */
//@NotThreadSafe
//public class UnsafeSequence {
//    private AtomicInteger value = new AtomicInteger();
//    /** Returns a unique value. */
//    public int getNext() {
//        return value.incrementAndGet();
//    }
//}

/** Synchronized getNext() */
//@NotThreadSafe
//public class UnsafeSequence {
//    private int value;
//    /** Returns a unique value. */
//    public synchronized int getNext() {
//        return value++;
//    }
//}

/** ReentrantLock on method body*/
//@NotThreadSafe
//public class UnsafeSequence {
//    private int value;
//    private final ReentrantLock lock = new ReentrantLock();
//    /** Returns a unique value. */
//    public int getNext() {
//        lock.lock();
//        try {
//            return value++;
//        } finally {
//            lock.unlock();
//        }
//    }
//}