package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.infra.results.LongResult4;

/**
 * Define operations under test and mapping to a LongResult
 */
public class BaseTable {

    private static boolean isChanged(final long changes, final int index) {
        if (index < 64) {
            return (changes & (1L << index)) != 0;
        } else {
            return false;
        }
    }

    /**
     * Set Long.new(1) to 0 field index and then update
     *
     * @param entry
     * @param res
     */
    public void set1(Entry entry, LongResult4 res) {
        entry.set(0, 1L);
        entry.update(this, res);
    }

    public void set2(Entry entry, LongResult4 res) {
        entry.set(1, 2L);
        entry.update(this, res);
    }

    public void update(Entry entry, LongResult4 r) {
        long _changes = entry.reset();
        r.r1 = isChanged(_changes, 0) ? 1 : -1;
        r.r2 = (long) entry.get(0);
        r.r3 = isChanged(_changes, 1) ? 2 : -1;
        r.r4 = (long) entry.get(1);
    }

}
