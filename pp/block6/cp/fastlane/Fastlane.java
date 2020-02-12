package block6.cp.fastlane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fastlane extends Thread {

    /** Counter that tracks updates of the master and helpers.
     * The value is odd when a transaction performs updates
     * and even otherwise. This variable is used for validation
     * by the helpers. */
    private AtomicInteger cntr;

    /** Array of monotonically increasing integers. Each memory
     * address is mapped to one entry in the array (by hashing the address
     * modulo the size of the array). The entry contains the value of the
     * counter cntr at the last time the address was written. */
    private ArrayList dirty;

    /** Lock to serialize commit attempts of helpers.
     * It is implemented as a MCS list-based queue lock [16]
     * and provides FIFO guarantees */
    private ConcurrentLinkedQueue<ReentrantLock> helpers;

    /** Lock to synchronize the master with the helper. Helpers
     * must acquire helpers first. It is implemented as a
     * TTAS (test-and-test-and-set) lock [12] and protects all
     * shared variables. */
    private TTASLock master;

    private class TTASLock {
        private AtomicBoolean m_locked;
        public TTASLock() {
            m_locked = new AtomicBoolean();
        }

        public void lock() {
            boolean acquired = false;
            while(!acquired) {
                if(!m_locked.get()) {
                    acquired = m_locked.compareAndSet(false, true);
                }
            }
        }

        public boolean tryLock() {
            if (m_locked.get()) {
                return false;
            }
            return m_locked.compareAndSet(false, true);
        }

        public void unlock() {
            m_locked.set(false);
        }
    }

    /** Identity of the current master thread. It must only be
     * modified after the master has been acquired. */
    private long masterID;

    private boolean abort = false;
    private int start = 0;
    private HashMap wr_set;
    private HashMap rd_set;

    public Fastlane() {
        this.cntr = new AtomicInteger();
        this.dirty = new ArrayList<Integer>();
        this.helpers = new ConcurrentLinkedQueue<>();
        this.master = new TTASLock();
        this.masterID = this.getId();
        this.wr_set = new HashMap();
        this.rd_set = new HashMap();
    }

    public void start(boolean pessimistic) {
        if (masterID == this.getId()) {
            master.lock();
            if (masterID == this.getId()) {
                masterStart();
                masterRead(0);
                masterWrite(0, 0);
                masterCommit();
                return; // CP_MASTER (jump to master code path)
            } else {
                master.unlock();
                do {
                    helperStart();
                    helperRead(0);
                    helperWrite(0, 0);
                    helperCommit();
                } while (!abort);
                return; //CP_HELPER (jump to helper code path)
            }
        } else if (pessimistic) {
            master.lock();
            masterID = this.getId();
            return; // CP_MASTER
        } else {
            helperStart();
            helperRead(0);
            helperWrite(0, 0);
            helperCommit();
            return; //CP_HELPER
        }
    }

    private void masterStart() {
        // Thread owns master at this point, no context saved
    }

    private int masterRead(int addr) {
        return addr;
    }

    private void masterWrite(Integer addr, int val) {
        if (cntr.decrementAndGet() % 2 != 1) {
            cntr.incrementAndGet();
        }
        dirty.add(addr.hashCode(), cntr.get());
        addr = val;
    }

    private void masterCommit() {
        if (cntr.get() % 2 == 1) {
            cntr.incrementAndGet();
        }
        master.unlock();
    }

    private void helperStart() {
        // setjmp(ctxt)     // Store the current context
        this.start = cntr.get();
    }

    private int helperRead(Integer addr) {
        if (wr_set.containsKey(addr)) {
            return (int) wr_set.get(addr.hashCode());
        }
        int val = addr;
        if ((int)dirty.get(addr) > this.start) {
            this.abort = true;
        }
        rd_set.put(addr, val);
        return val;
    }

    private void helperWrite(Integer addr, int val) {
        if ((int)dirty.get(addr.hashCode()) > this.start) {
            this.abort = true;
        }
        wr_set.put(addr, val);
    }

    private boolean validate() {
        if (cntr.get() <= this.start) {
            return true;
        } else {
            HashMap<Integer, Integer> temp = new HashMap<>();
            temp.putAll(wr_set);
            temp.putAll(rd_set);
            for (Integer addr : temp.keySet()) {
                if ((int)dirty.get(addr.hashCode()) > this.start) {
                    return false;
                }
            }
        }
        return true;
    }

    private void helperCommit() {
        if (wr_set.keySet().isEmpty()) {    // read only
            return;                         // commit immediately
        }
        for (ReentrantLock helper : helpers) {
            helper.lock();                  // acquire helpers
        }
        master.lock();                      // acquire master

        if (wr_set.keySet().isEmpty()) {    // Read-only transaction?
            return;                         // Commit immediately
        }

        for (ReentrantLock helper : helpers) {
            helper.lock();                  // acquire helpers
        }
        master.lock();                      // acquire master
        if (!validate()) {
            master.unlock(); // Release master
        }

        for (ReentrantLock helper : helpers) {
            helper.unlock();                // Release helpers
        }
        this.abort = true;                  // Abort and restart
        cntr.incrementAndGet();             // Helper sets cntr odd

        for (Object addr : wr_set.keySet()) {
            dirty.set(addr.hashCode(), cntr.get()); // Update dirty[]
            addr = wr_set.get(addr);        // Update memory
        }
        cntr.incrementAndGet();             // Helper sets cntr even
        master.unlock();                    // Release master
        for (ReentrantLock helper : helpers) {
            helper.unlock();                // Release helpers
        }
    }
}
