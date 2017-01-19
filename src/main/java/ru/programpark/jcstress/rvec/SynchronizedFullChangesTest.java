package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult4;


/**
 * get, set and reset synchronized on entry
 */
@JCStressTest
@Outcome(id = "1, 1, -1, 2", expect = Expect.ACCEPTABLE, desc = "first changed, second change invisible")
@Outcome(id = "1, 1, -1, -1", expect = Expect.ACCEPTABLE, desc = "first changed, second invisible")
@Outcome(id = "-1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first  change invisible")
@Outcome(id = "-1, -1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first invisible")
@Outcome(id = "1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "both changed and invisible")
@Outcome(id = ".*", expect = Expect.ACCEPTABLE_INTERESTING, desc = "interesting behaviour")
public class SynchronizedFullChangesTest extends BaseTable {

    @Actor
    public void actor1(FEntry myState, LongResult4 res) {
        set1(myState, res);
    }

    @Actor
    public void actor2(FEntry myState, LongResult4 res) {
        set2(myState, res);
    }

    @State
    public static class FEntry extends UnsyncEntry {

        public synchronized Object get(int idx) {
            return super.get(idx);
        }

        public synchronized void set(int idx, Object o) {
            super.set(idx, o);
        }

        public synchronized long reset() {
            return super.reset();
        }

    }

}
