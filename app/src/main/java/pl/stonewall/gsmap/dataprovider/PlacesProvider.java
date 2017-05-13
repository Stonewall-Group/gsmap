package pl.stonewall.gsmap.dataprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

public class PlacesProvider extends ContentProvider {
    /*
    static final String PROVIDER_NAME = "pl.stonewall.gsmap.PlacesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/placess";
    static final Uri CONTENT_URI = Uri.parse(URL);
*/
    private static final int PLACES = 1;
    private static final int PLACE_ID = 2;

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PlacesProviderContract.AUTHORITY, "places", PLACES);
        uriMatcher.addURI(PlacesProviderContract.AUTHORITY, "places/#", PLACE_ID);
    }

    private PlacesDatabaseHelper dbHelper;

    public PlacesProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PLACES:
                return PlacesProviderContract.Places.CONTENT_TYPE;
            case PLACE_ID:
                return PlacesProviderContract.Places.CONTENT_PLACE_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new PlacesDatabaseHelper(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case PLACES:
                builder.setTables(PlacesProviderContract.Places.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = PlacesProviderContract.Places.SORT_ORDER_DEFAULT;
                }
                break;
            case PLACE_ID:
                builder.setTables(PlacesProviderContract.Places.TABLE_NAME);
                builder.appendWhere(PlacesProviderContract.Places._ID + " = "
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
