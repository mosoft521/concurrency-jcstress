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
public class VolatileChangesTest extends BaseTable {

    @Actor
    public void actor1(VEntry myState, LongResult4 res) {
        set1(myState, res);
    }

    @Actor
    public void actor2(VEntry myState, LongResult4 res) {
        set2(myState, res);
    }


    @State
    public static class VEntry implements Entry {
        final Object[] values = new Object[UnsyncEntry.SIZE];
        volatile long changes = 0;

        public Object get(int idx) {
            Object value = values[idx];
            return value == null ? -1L : value;
        }

        public void set(int idx, Object o) {
            values[idx] = o;
            changes |= (1 << idx);
        }

        public long reset() {
            long _changes = changes;
            changes = 0;
            return _changes;
        }

        @Override
        public void update(BaseTable table, LongResult4 res) {
            table.update(this, res);
        }
    }

}
