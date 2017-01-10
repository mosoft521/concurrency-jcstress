package ru.programpark.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult4;


/**
 * Created by kozyr on 10.01.2017.
 */
@JCStressTest
@Outcome(id = "1, 2, -1, 7", expect = Expect.ACCEPTABLE, desc = "all set visible")
@Outcome(expect = Expect.FORBIDDEN, desc = "other values")
public class EntryChangesTest {
    public final static int SIZE = 64;

    @Actor
    public void actor1(UnsyncEntry myState) {
        myState.set(0, 1L);
    }

    @Actor
    public void actor2(UnsyncEntry myState) {
        myState.set(1, 2L);
    }

    @Actor
    public void actor4(UnsyncEntry myState) {
        myState.set(2, -1L);
    }

    @Arbiter
    public void arbiter(UnsyncEntry myState, LongResult4 res) {
        res.r1 = (long) myState.get(0);
        res.r2 = (long) myState.get(1);
        res.r3 = (long) myState.get(2);
        res.r4 = myState.changes;
    }

    @State
    public static class UnsyncEntry {
        final Object[] values = new Object[SIZE];
        long changes = 0;

        public Object get(int idx) {
            return values[idx];
        }

        public synchronized void set(int idx, Object o) {
            values[idx] = o;
            changes |= (1 << idx);
        }
    }

}
