/*
 * Activity.java : Basic abstract class to create Activity as an object
 * Methods       : none
 * Sub-class     : ActivityEntry - Main database of history table
 *                 BestEntry     - Database to hold best statistic
 */

package mdpcw2.mytracker.non_activity;

import android.provider.BaseColumns;

public final class ActivityContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ActivityContract(){}

    /* Inner class that defines the HISTORY table contents */
    public static class ActivityEntry implements BaseColumns{
        public static final String DATABASE_NAME = "records.db";
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STEP = "step";
        public static final String COLUMN_NAME_DISTANCE = "distance";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_CALORIES = "calories";
        public static final String COLUMN_NAME_GPX = "gpx";
    }

    /* Inner class that defines the BEST-record table contents */
    public static class BestEntry implements BaseColumns{
        public static final String DATABASE_NAME = "bestrecord.db";
        public static final String TABLE_NAME = "best";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_STEP = "step";
        public static final String COLUMN_NAME_DISTANCE = "distance";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_CALORIES = "calories";
    }
}
