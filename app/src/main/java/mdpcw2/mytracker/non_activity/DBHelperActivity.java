/*
 * DBHelper.java  : Handle the DB with queries operation
 *
 * Methods        : addActivity(), findActivity(), deleteActivity(), updateActivity(), display()
 *
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

    //this method add a new recipe into the database, respective column
    public void addActivity(Activities activities){
        ContentValues values = new ContentValues();
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DATE, activities.getDate());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_STEP, activities.getStep());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DISTANCE, activities.getDistance());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_DURATION, activities.getDuration());
        values.put(ActivityContract.ActivityEntry.COLUMN_NAME_CALORIES, activities.getCalories());

        myCR.insert(MyContentProvider.CONTENT_URI,values);
    }

    //this method return a Recipe as object
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
            Log.d("MyRecipe",e.toString());
        }
        return activities;
    }

    //this method deletes the specified recipe
    public boolean deleteActivity(int _id){
        boolean result = false;
        String selection = "_id = \"" +_id+ "\"";

        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,selection,null);

        if (rowsDeleted >0){result = true;}
        return result;
    }

    //this method update the current recipe
    public boolean updateActivity(int _id,String date, int step, int distance, int duration, int calories, String gpx){
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

    //this method returns array of Recipe(object) to be displayed
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
                Log.d("MyRecipe","cursor : inside IF");
                do{
                    Activities activities = new Activities();
                    Log.d("MyRecipe","cursor: inside DO");
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
            Log.d("MyRecipe",e.toString());
        }
        return result;
    }
}
