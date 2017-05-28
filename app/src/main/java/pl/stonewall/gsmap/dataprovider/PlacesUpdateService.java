package pl.stonewall.gsmap.dataprovider;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class PlacesUpdateService extends IntentService {

    private static final String ACTION_UPDATE = "pl.stonewall.gsmap.dataprovider.action.UPDATE";
    private static final String CLASS_NAME = "PlacesUpdateService";

    private static final String PROVIDER_URL = "http://gsmaptest.grupa-stonewall.pl/places/changes.json";

    private PlacesDatabaseHelper dbHelper;

    public PlacesUpdateService() {
        super("PlacesUpdateService");
        dbHelper = new PlacesDatabaseHelper(this);
    }

    /**
     * Starts this service to perform action Update with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, PlacesUpdateService.class);
        intent.setAction(ACTION_UPDATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                handleActionUpdate();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate() {
        String jsonStr = getJsonStringFromUrl(PROVIDER_URL);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray places = jsonObj.getJSONArray("places");
                for (int i = 0; i < places.length(); i++) {
                    JSONObject place = places.getJSONObject(i);

                    //put to provider...
                }
            } catch (final JSONException e) {
                Log.e(CLASS_NAME, "Json parsing error: " + e.getMessage());
            }
        }
    }

    private String getJsonStringFromUrl(String urlStr) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            Log.e(CLASS_NAME, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(CLASS_NAME, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(CLASS_NAME, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(CLASS_NAME, "Exception: " + e.getMessage());
        }
        return stringBuilder.toString();
    }

    private void putIntoDatabase(String jsonStr) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray places = jsonObj.getJSONArray("places");
                for (int i = 0; i < places.length(); i++) {
                    JSONObject place = places.getJSONObject(i);
                    db.replace(PlacesProviderContract.Places.TABLE_NAME, null, getContentValue(place));
                }
                db.setTransactionSuccessful();
            } catch (final JSONException e) {
                Log.e(CLASS_NAME, "Json parsing error: " + e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
    }

    private static ContentValues getContentValue(JSONObject place) throws JSONException {
        ContentValues cv = new ContentValues();
        cv.put(PlacesProviderContract.Places._ID, place.getInt(PlacesProviderContract.Places._ID));
        cv.put(PlacesProviderContract.Places.COLUMN_NAME, place.getString(PlacesProviderContract.Places.COLUMN_NAME));
        cv.put(PlacesProviderContract.Places.COLUMN_DESCRIPTION, place.getString(PlacesProviderContract.Places.COLUMN_DESCRIPTION));
        cv.put(PlacesProviderContract.Places.COLUMN_IMAGE, place.getString(PlacesProviderContract.Places.COLUMN_IMAGE));
        cv.put(PlacesProviderContract.Places.COLUMN_LOGO, place.getInt(PlacesProviderContract.Places.COLUMN_LOGO));
        cv.put(PlacesProviderContract.Places.COLUMN_ADDRESS, place.getInt(PlacesProviderContract.Places.COLUMN_ADDRESS));
        cv.put(PlacesProviderContract.Places.COLUMN_CATEGORY, place.getInt(PlacesProviderContract.Places.COLUMN_CATEGORY));
        cv.put(PlacesProviderContract.Places.COLUMN_FACEBOOK, place.getInt(PlacesProviderContract.Places.COLUMN_FACEBOOK));
        cv.put(PlacesProviderContract.Places.COLUMN_LATITUDE, place.getInt(PlacesProviderContract.Places.COLUMN_LATITUDE));
        cv.put(PlacesProviderContract.Places.COLUMN_LONGITUDE, place.getInt(PlacesProviderContract.Places.COLUMN_LONGITUDE));
        cv.put(PlacesProviderContract.Places.COLUMN_PHONE, place.getInt(PlacesProviderContract.Places.COLUMN_PHONE));
        cv.put(PlacesProviderContract.Places.COLUMN_WWW, place.getInt(PlacesProviderContract.Places.COLUMN_WWW));
        cv.put(PlacesProviderContract.Places._VERSION, place.getInt(PlacesProviderContract.Places._VERSION));
        return cv;
    }


}
