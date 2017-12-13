/*
 * DBHelper.java  : Handle the DB with queries operation
 *
 * Methods        : addActivity()      : add a new activity (as object) into database
 *                  findActivity()     : retrieve a record
 *                  findActivityBest() : get the best record
 *                  deleteActivity()   : delete specified record
 *                  updateActivity()   : update specified record
 *                  display()          : display all records
 */

package mdpcw2.mytracker.non_activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelperActivity extends SQLiteOpenHelper{

    //Global variables
    private ContentResolver myCR;

    //The database version.
    public static final int DATABASE_VERSION = 1;

    //public constructor
    public DBHelperActivity(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, ActivityContract.ActivityEntry.DATABASE_NAME,null,DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    //this method initializes the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                ActivityContract.ActivityEntry.TABLE_NAME + "(" +
                ActivityContract.ActivityEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                ActivityContract.ActivityEntry.COLUMN_NAME_DATE + " TEXT," +
                ActivityContract.ActivityEntry.COLUMN_NAME_STEP + " INTEGER," +
                ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE + " INTEGER," +
                ActivityContract.ActivityEntry.COLUMN_NAME_DURATION + " INTEGER," +
                ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES + " INTEGER" + ")";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    //this method detect any upgrade version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ActivityContract.ActivityEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //this method add a new Activity into the database, respective column
    public void addActivity(Activities activities){
        ContentValues values = new ContentValues();
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DATE, activities.getDate());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_STEP, activities.getStep());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE, activities.getDistance());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DURATION, activities.getDuration());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES, activities.getCalories());

        myCR.insert(MyContentProvider.CONTENT_URI,values);
    }

    //this method return a Activity as object
    public Activities findActivity(int _id){
        String[] projection = {
                ActivityContract.ActivityEntry.COLUMN_NAME_ID,
                ActivityContract.ActivityEntry.COLUMN_NAME_DATE,
                ActivityContract.ActivityEntry.COLUMN_NAME_STEP,
                ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE,
                ActivityContract.ActivityEntry.COLUMN_NAME_DURATION,
                ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES};

        String selection = "_id = \"" +_id+"\"";

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,
                projection,
                selection,
                null,
                null);

        Activities activities = new Activities();

        try {
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                activities.set_id(Integer.parseInt(cursor.getString(0)));
                activities.setDate(cursor.getString(1));
                activities.setStep(cursor.getString(2));
                activities.setDistance(cursor.getString(3));
                activities.setDuration(cursor.getString(4));
                activities.setCalories(cursor.getString(5));
                cursor.close();
            }else{
                activities = null;
            }
        }catch (Exception e){
            Log.d("MyTracker",e.toString());
        }
        return activities;
    }

    //this method return a best Activity as object
    //by passing the desired parameter
    public String findActivityBest(String column){
        String result = "null";
        String query = "SELECT * FROM "+ ActivityContract.ActivityEntry.TABLE_NAME+" ORDER BY "+column +" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        try{
            switch (column){
                case "step":
                    Log.d("MyTracker",String.valueOf(cursor.getInt(2)));
                    result = String.valueOf(cursor.getInt(2));
                    break;
                case "distance":
                    Log.d("MyTracker",String.valueOf(cursor.getInt(3)));
                    result = String.valueOf(cursor.getInt(3));
                    break;
                case "calories":
                    Log.d("MyTracker",String.valueOf(cursor.getInt(5)));
                    result = String.valueOf(cursor.getInt(5));
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            Log.d("MyTracker",e.toString());
        }
        db.close();
        cursor.close();
        return result;
    }

    //this method deletes the specified record
    public boolean deleteActivity(int _id){
        boolean result = false;
        String selection = "_id = \"" +_id+ "\"";

        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,selection,null);

        if (rowsDeleted >0){result = true;}
        return result;
    }

    //this method update the current Activity
    //currently not used, just put in case for future usage/reference
    public boolean updateActivity(int _id,String date, int step, int distance, int duration, int calories){
        boolean result = false;
        String selection = "_id = \""+_id+" \"";
        ContentValues values = new ContentValues();
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DATE,date);
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_STEP,step);
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE,distance);
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DURATION,duration);
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES,calories);

        int rowsUpdated = myCR.update(MyContentProvider.CONTENT_URI,values,selection,null);

        if(rowsUpdated>0){result=true;}
        return result;
    }

    //this method returns array of Activities(object) to be displayed
    public ArrayList<Activities> display(){
        String[] projection = {
                ActivityContract.ActivityEntry.COLUMN_NAME_ID,
                ActivityContract.ActivityEntry.COLUMN_NAME_DATE,
                ActivityContract.ActivityEntry.COLUMN_NAME_STEP,
                ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE,
                ActivityContract.ActivityEntry.COLUMN_NAME_DURATION,
                ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES};

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,projection,null,null,null);

        ArrayList<Activities> result = new ArrayList<Activities>();
        try{
            if(cursor.moveToFirst()){
                Log.d("MyTracker","cursor : inside IF");
                do{
                    Activities activities = new Activities();
                    Log.d("MyTracker","cursor: inside DO");
                    activities.set_id(Integer.parseInt(cursor.getString(0)));
                    activities.setDate(cursor.getString(1));
                    activities.setStep(cursor.getString(2));
                    activities.setDistance(cursor.getString(3));
                    activities.setDuration(cursor.getString(4));
                    activities.setCalories(cursor.getString(5));
                    result.add(activities);
                }while(cursor.moveToNext());
            }cursor.close();
        }catch (Exception e){
            Log.d("MyTracker",e.toString());
        }
        return result;
    }
}
