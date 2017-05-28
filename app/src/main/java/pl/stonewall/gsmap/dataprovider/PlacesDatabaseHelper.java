package pl.stonewall.gsmap.dataprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pziemba on 13.05.2017.
 */
class PlacesDatabaseHelper extends SQLiteOpenHelper {

    /** Schema version. */
    public static final int DATABASE_VERSION = 1;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "gsmap.db";

    /** SQL statement to create "places" table. */
    static final String CREATE_DB_TABLE_PLACES =
            " CREATE TABLE " + PlacesProviderContract.Places.TABLE_NAME + " ( " +
                    PlacesProviderContract.Places._ID + " INTEGER PRIMARY KEY, " +
                    PlacesProviderContract.Places.COLUMN_NAME + " TEXT NOT NULL, " +
                    PlacesProviderContract.Places.COLUMN_DESCRIPTION + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_LOGO + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_IMAGE + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_LATITUDE + " REAL," +
                    PlacesProviderContract.Places.COLUMN_LONGITUDE + " REAL," +
                    PlacesProviderContract.Places.COLUMN_CATEGORY + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_ADDRESS + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_PHONE + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_FACEBOOK + " TEXT," +
                    PlacesProviderContract.Places.COLUMN_WWW + " www TEXT," +
                    PlacesProviderContract.Places._VERSION + " version INTEGER );";


    /** SQL statement to drop "places" table. */
    private static final String DROP_DB_TABLE_PLACES =
            "DROP TABLE IF EXISTS " + PlacesProviderContract.Places.TABLE_NAME;


    PlacesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE_PLACES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_DB_TABLE_PLACES);
        onCreate(db);
    }
}
