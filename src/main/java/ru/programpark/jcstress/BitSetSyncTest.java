package ru.programpark.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import java.util.BitSet;


/**
 * Created by kozyr on 10.01.2017.
 */
@JCStressTest
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "all set")
@Outcome(expect = Expect.FORBIDDEN, desc = "other values")
public class BitSetSyncTest {

    @Actor
    public void actor1(UnsyncEntry myState) {
        synchronized (myState) {
            myState.state.set(1);
        }
    }

    @Actor
    public void actor2(UnsyncEntry myState) {
        synchronized (myState) {
            myState.state.set(2);
        }
    }

    @Arbiter
    public void arbiter(UnsyncEntry myState, BooleanResult2 r) {
        r.r1 = myState.state.get(1);
        r.r2 = myState.state.get(2);
    }

    @State
    public static class UnsyncEntry {
        final BitSet state = new BitSet();
    }

}
