/*
 * MyContentProvider: ContentProvider class to interact with database
 *
 * Methods:         :  insert(), query()
 */

package mdpcw2.mytracker.non_activity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

    //Global Variables
    private DBHelperActivity myDB;

    private static final String AUTHORITY = "mdpcw2.mytracker.non_activity.MyContentProvider";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + ActivityContract.ActivityEntry.TABLE_NAME);

    public static final int ACTIVITY = 1;
    public static final int ACTIVITY_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, ActivityContract.ActivityEntry.TABLE_NAME, ACTIVITY);
        sURIMatcher.addURI(AUTHORITY, ActivityContract.ActivityEntry.TABLE_NAME + "/#",
                ACTIVITY_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Handle requests to delete selected row.
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case ACTIVITY:
                rowsDeleted = sqlDB.delete(ActivityContract.ActivityEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case ACTIVITY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ActivityContract.ActivityEntry.TABLE_NAME,
                            ActivityContract.ActivityEntry.COLUMN_NAME_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ActivityContract.ActivityEntry.TABLE_NAME,
                            ActivityContract.ActivityEntry.COLUMN_NAME_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // Handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //Handle requests to insert a new row.
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = myDB.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case ACTIVITY:
                id = sqlDB.insert(ActivityContract.ActivityEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ActivityContract.ActivityEntry.TABLE_NAME + "/" + id);
    }

    @Override
    public boolean onCreate() {
        //Initialize your content provider on startup.
        myDB = new DBHelperActivity(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //Handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ActivityContract.ActivityEntry.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case ACTIVITY_ID:
                queryBuilder.appendWhere(ActivityContract.ActivityEntry.COLUMN_NAME_ID + "="
                        + uri.getLastPathSegment());
                break;
            case ACTIVITY:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
