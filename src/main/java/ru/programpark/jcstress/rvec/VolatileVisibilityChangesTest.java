package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult4;


/**
 * Entry with volatile changes
 */
@JCStressTest
@Outcome(id = "1, 1, -1, 2", expect = Expect.ACCEPTABLE, desc = "first changed, second change invisible")
@Outcome(id = "1, 1, -1, -1", expect = Expect.ACCEPTABLE, desc = "first changed, second invisible")
@Outcome(id = "-1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first  change invisible")
@Outcome(id = "-1, -1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first invisible")
@Outcome(id = "1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "both changed and invisible")
@Outcome(id = ".*", expect = Expect.ACCEPTABLE_INTERESTING, desc = "interesting behaviour")
public class VolatileVisibilityChangesTest extends BaseTable {

    @Actor
    public void actor1(VEntry myState, LongResult4 res) {
        set1(myState, res);
    }

    @Actor
    public void actor2(VEntry myState, LongResult4 res) {
        set2(myState, res);
    }


    @State
    public static class VEntry extends UnsyncEntry {
        volatile long barrier = 1;

        public Object get(int idx) {
            // do volatile force visibility?
            if (barrier > 0) {
                return super.get(idx);
            } else {
                return 0;
            }
        }

        public void set(int idx, Object o) {
            super.set(idx, o);
            barrier = 1;
        }

        public long reset() {
            long _changes = super.reset();
            barrier = 2;
            return _changes;
        }
    }

}