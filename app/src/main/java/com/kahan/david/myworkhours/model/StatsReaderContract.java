package com.kahan.david.myworkhours.model;

import android.provider.BaseColumns;

/**
 * Created by david on 21/04/2018.
 */
//TODO: different approach, probably should have use it.
public final class StatsReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private StatsReaderContract() {
    }
    /* Inner class that defines the table contents */
    public static class StatEntry implements BaseColumns {
        public static final String TABLE_NAME = "stats";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DURATION = "duration";
    }
}
