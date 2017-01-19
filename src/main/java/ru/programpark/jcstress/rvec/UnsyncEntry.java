package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LongResult4;

@State
public class UnsyncEntry implements Entry {
    public final static int SIZE = 64;
    private final Object[] values = new Object[SIZE];
    private long changes = 0;

    @Override
    public Object get(int idx) {
        Object value = values[idx];
        return value == null ? -1L : value;
    }

    @Override
    public void set(int idx, Object o) {
        values[idx] = o;
        changes |= (1 << idx);
    }

    @Override
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
