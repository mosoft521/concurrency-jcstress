package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.infra.results.LongResult4;


/**
 * set and update synchronized on entry
 */
@JCStressTest
@Outcome(id = "1, 1, -1, 2", expect = Expect.ACCEPTABLE, desc = "first changed, second change invisible")
@Outcome(id = "1, 1, -1, -1", expect = Expect.ACCEPTABLE, desc = "first changed, second invisible")
@Outcome(id = "-1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first  change invisible")
@Outcome(id = "-1, -1, 2, 2", expect = Expect.ACCEPTABLE, desc = "second changed, first invisible")
@Outcome(id = "1, 1, 2, 2", expect = Expect.ACCEPTABLE, desc = "both changed and invisible")
@Outcome(id = ".*", expect = Expect.ACCEPTABLE_INTERESTING, desc = "interesting behaviour")
public class SynchronizedSetUpdateChangesTest extends BaseTable {

    @Actor
    public void actor1(UnsyncEntry myState, LongResult4 res) {
        synchronized (myState) {
            set1(myState, res);
        }
    }

    @Actor
    public void actor2(UnsyncEntry myState, LongResult4 res) {
        synchronized (myState) {
            set2(myState, res);
        }
    }

}
