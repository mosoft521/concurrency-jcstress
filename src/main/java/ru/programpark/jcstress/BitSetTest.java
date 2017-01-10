package ru.programpark.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import java.util.BitSet;


/**
 * Created by kozyr on 10.01.2017.
 */
@JCStressTest
@Outcome(id = "false, true", expect = Expect.ACCEPTABLE_INTERESTING, desc = "other values")
@Outcome(id = "true, false", expect = Expect.ACCEPTABLE_INTERESTING, desc = "other values")
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE_INTERESTING, desc = "other values")
@Outcome(expect = Expect.FORBIDDEN, desc = "other values")
public class BitSetTest {

    @Actor
    public void actor1(UnsyncEntry myState) {
        myState.state.set(1);
    }

    @Actor
    public void actor2(UnsyncEntry myState) {
        myState.state.set(2);
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
