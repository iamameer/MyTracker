/*
 * ActivityContract.java : Basic abstract class serving as Contract Class
 * Methods               : none
 * Sub-class             : ActivityEntry - Main database of history table
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
    }

}
