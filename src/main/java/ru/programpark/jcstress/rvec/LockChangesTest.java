package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * set and update guarded by ReentrantLock
 */
@JCStressTest
@Outcome(id = "1, 1, -1, 2", expect = Expect.ACCEPTABLE, desc = "first changed, second change invisible")
@Outcome(id = "1, 1, -1, -1", expect = Expect.ACCEPTABLE, desc = "first changed, second invisible")
@Outcome(id = "-1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first  change invisible")
@Outcome(id = "-1, -1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first invisible")
@Outcome(id = "1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "both changed and invisible")
@Outcome(id = ".*", expect = Expect.ACCEPTABLE_INTERESTING, desc = "interesting behaviour")
public class LockChangesTest extends BaseTable {

    @Actor
    public void actor1(LockEntry myState, LongResult4 res) {
        Lock lock = myState.lock;
        lock.lock();
        try {
            set1(myState, res);
        } finally {
            lock.unlock();
        }
    }

    @Actor
    public void actor2(LockEntry myState, LongResult4 res) {
        Lock lock = myState.lock;
        lock.lock();
        try {
            set2(myState, res);
        } finally {
            lock.unlock();
        }
    }


    @State
    public static class LockEntry extends UnsyncEntry {
        final public Lock lock = new ReentrantLock();
    }

}
