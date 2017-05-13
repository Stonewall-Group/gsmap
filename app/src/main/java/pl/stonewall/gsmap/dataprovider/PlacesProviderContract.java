package pl.stonewall.gsmap.dataprovider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.content.ContentResolver;

/**
 * Created by pziemba on 13.05.2017.
 */

public class PlacesProviderContract {
    public static final String AUTHORITY = "pl.stonewall.gsmap.placesProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final class Places implements BaseColumns {
        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PlacesProviderContract.CONTENT_URI, "places");
        /**
         * The mime type of a directory of photos.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/places";
        /**
         * The mime type of a single photo.
         */
        public static final String CONTENT_PLACE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/places";
        /**
         * The column descriptions.
         */
        public static final String TABLE_NAME = "places";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LOGO = "logo";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_FACEBOOK = "facebook";
        public static final String COLUMN_WWW = "www";
        public static final String _VERSION = "version";
        /**
         * A projection of all columns in the photos table.
         */
        public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME, COLUMN_DESCRIPTION,
            COLUMN_LOGO, COLUMN_IMAGE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_CATEGORY,
            COLUMN_ADDRESS, COLUMN_PHONE, COLUMN_FACEBOOK, COLUMN_WWW, _VERSION};
        public static final String SORT_ORDER_DEFAULT = COLUMN_NAME + " asc";
    }

}
