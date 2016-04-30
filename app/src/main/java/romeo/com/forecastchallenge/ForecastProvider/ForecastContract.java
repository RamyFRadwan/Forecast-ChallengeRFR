package romeo.com.forecastchallenge.ForecastProvider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class ForecastContract {

    public static final String CONTENT_AUTHORITY = "romeo.com.forecastchallenge.DATA";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FORECAST = "forecast";


    public  static final class ForecastEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FORECAST).build();



    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + BASE_CONTENT_URI;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + BASE_CONTENT_URI;

              public static final String TABLE_NAME = "forecast";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_icon = "icon";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_HI = "hi";
    public static final String COLUMN_LOW = "low";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TEXT = "text";

    public static Uri buildForecastUri(long id) {
        return
                ContentUris.withAppendedId(CONTENT_URI, id);

    }


}
}
