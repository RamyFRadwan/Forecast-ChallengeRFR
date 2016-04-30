package romeo.com.forecastchallenge;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import romeo.com.forecastchallenge.ForecastProvider.ForecastContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ArrayList<ForecastModel> forecasts = new ArrayList<>();
    RecyclerView recyclerView;
    public static String Poster;

    public MainActivityFragment() {
    }

    String forecastJSON = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.wunderground.com/api/838ed9367e8876bf%20/forecast/q/EG/Cairo.json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        forecastJSON = response;
                        try {

                            // Clearing Database before inserting new values
                            getContext().deleteDatabase(ForecastContract.ForecastEntry.TABLE_NAME);
                            getForecastData(forecastJSON);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fetchOfflineData();
                ForecastAdapter mAdapter = new ForecastAdapter(forecasts, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                Toast.makeText(getContext(), "Error in connection", Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);

        recyclerView = (RecyclerView) view.findViewById(R.id.forecastRV);
        return view;

    }

    String title;

    private Collection<ForecastModel> getForecastData(String forecastsJSON)
            throws JSONException {

        //         These are the names of the JSON objects that need to be extracted.
        final String MDB_LIST = "simpleforecast";
        final String MDB_LIST2 = "forecastday";

        final JSONObject forecastJson = new JSONObject(forecastsJSON);
        JSONObject forecast = forecastJson.getJSONObject("forecast");
        JSONObject forecastTree1 = forecast.getJSONObject("txt_forecast");
        JSONArray jsonArray = forecastTree1.getJSONArray(MDB_LIST2);
        JSONObject forecastTree2 = forecast.getJSONObject(MDB_LIST);
        JSONArray jsonArray1 = forecastTree2.getJSONArray(MDB_LIST2);
        Log.e("TTTTTTTTTTTTTTT", jsonArray.getString(1));
        // Getting root tags for condition, date, hi/low, iconURL from JSONArray1 and getting it for text, title from jsonArray

        for (int i = 0; i < jsonArray1.length(); i++) {
            JSONObject forecastObj1 = jsonArray1.getJSONObject(i);
            final ForecastModel forecastModel = ForecastModel.forecastDeserialize(forecastObj1);
            JSONObject forecastObj = jsonArray.getJSONObject(i);
            title = forecastObj.getString("title");
            text = forecastObj.getString("fcttext_metric");
            forecastModel.setTitle(title);
            forecastModel.setText(text);


            //inserting bulk data into db for offline use

            com.squareup.picasso.Target target = new Target() {

                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + forecastModel.condition + ".gif");
                            Poster = file.getPath();

                            try {
                                file.createNewFile();
                                FileOutputStream ostream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                                forecastModel.setIconURLOFF(Environment.getExternalStorageDirectory().getPath() + "/" + forecastModel.condition + ".gif" );
                                Log.e("Icon offline path", forecastModel.getIconURLOFF());
                                ostream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

                public void onBitmapFailed(Drawable errorDrawable) {
                }

                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    if (placeHolderDrawable != null) {
                    }
                }
            };

            Picasso.with(getActivity())
                    .load(forecastModel.getIconurl())
                    .into(target);

            ContentValues values = new ContentValues();
            values.put(ForecastContract.ForecastEntry.COLUMN_TITLE, forecastModel.getTitle());
            values.put(ForecastContract.ForecastEntry.COLUMN_icon, forecastModel.getIconURLOFF());
            values.put(ForecastContract.ForecastEntry.COLUMN_CONDITION, forecastModel.getCondition());
            values.put(ForecastContract.ForecastEntry.COLUMN_HI, forecastModel.getHigh());
            values.put(ForecastContract.ForecastEntry.COLUMN_LOW, forecastModel.getLow());
            values.put(ForecastContract.ForecastEntry.COLUMN_DATE, forecastModel.getDate());
            values.put(ForecastContract.ForecastEntry.COLUMN_TEXT, forecastModel.getText());


            getActivity().getContentResolver().insert(ForecastContract.ForecastEntry.CONTENT_URI, values);

            forecasts.add(forecastModel);
        }


        ForecastAdapter mAdapter = new ForecastAdapter(forecasts, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return forecasts;

    }


    String Title;
    int high;
    int low;
    String text;
    String condition;
    String iconurl;
    String date;
    int id;

    private void fetchOfflineData() {
        List<ForecastModel> favouriteForecastModels = new ArrayList<>();
        final Cursor c = getActivity().getContentResolver()
                .query(ForecastContract.ForecastEntry.CONTENT_URI, null, null, null, null);

        if (c.moveToFirst()) {

            do {
                condition = c.getString(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_CONDITION));
                Title = c.getString(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_TITLE));
                text = c.getString(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_TEXT));
                date = c.getString(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_DATE));
                high = c.getInt(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_HI));
                low = c.getInt(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_LOW));
                iconurl = c.getString(c.getColumnIndex(ForecastContract.ForecastEntry.COLUMN_icon));

                id = c.getInt(c.getColumnIndex(ForecastContract.ForecastEntry._ID));
                ForecastModel forecast = new ForecastModel(Title, high, low, text, condition, iconurl, date);
                forecasts.add(forecast);
            } while (c.moveToNext());
        }

    }
}

