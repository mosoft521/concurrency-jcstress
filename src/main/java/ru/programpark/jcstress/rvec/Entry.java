package ru.programpark.jcstress.rvec;

import org.openjdk.jcstress.infra.results.LongResult4;

/**
 * Created by kozyr on 19.01.2017.
 */
public interface Entry {

    Object get(int idx);

    void set(int idx, Object o);

    long reset();

    void update(BaseTable table, LongResult4 res);
}

