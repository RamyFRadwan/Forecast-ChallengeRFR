package romeo.com.forecastchallenge.ForecastProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    static final String DATABASE_NAME = "forecast.db";



    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_FORECAST_TABLE = "CREATE TABLE " +    ForecastContract.ForecastEntry.TABLE_NAME + " (" +
                ForecastContract.ForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ForecastContract.ForecastEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ForecastContract.ForecastEntry.COLUMN_icon + " TEXT, " +
                ForecastContract.ForecastEntry.COLUMN_CONDITION + " TEXT, " +
                ForecastContract.ForecastEntry.COLUMN_HI + " INTEGER, " +
                ForecastContract.ForecastEntry.COLUMN_LOW + " INTEGER, " +
                ForecastContract.ForecastEntry.COLUMN_DATE + " TEXT, " +
                ForecastContract.ForecastEntry.COLUMN_TEXT + " TEXT);";
        db.execSQL(CREATE_FORECAST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        final String UPGRADE_FORECAST_TABLE ="DROP TABLE IF EXISTS " +       ForecastContract.ForecastEntry.TABLE_NAME;
        db.execSQL(UPGRADE_FORECAST_TABLE);
        onCreate(db);

    }
}
