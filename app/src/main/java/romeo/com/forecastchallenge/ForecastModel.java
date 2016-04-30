package romeo.com.forecastchallenge;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RamyFRadwan on 29/04/2016.
 */
public class ForecastModel{
    private static final String date_key = "pretty";
    private static final String condition_key = "conditions";
    private static final String icon_key = "icon_url";
    private static final String high_key = "high";
    private static final String low_key = "low";
    private static final String celesius_key = "celsius";
    private static final String text_key = "fcttext_metric";

    String Title;
    int high;
    int low;
    String text;
    String condition;
    String iconurl;

    public String getIconURLOFF() {
        return iconURLOFF;
    }

    public void setIconURLOFF(String iconURLOFF) {
        this.iconURLOFF = iconURLOFF;
    }

    String iconURLOFF;
    String date;


    //Class constructor
    public ForecastModel ( String title, int high, int low, String text, String condition, String iconurl, String date ){
        setCondition(condition);
        setDate(date);
        setHigh(high);
        setLow(low);
        setText(text);
        setIconurl(iconurl);
        setTitle(title);

        Log.e("ssd",title);
    }
    public ForecastModel ( String title, int high, int low, String text, String condition, String iconURLOFF, String date, String iconurl ){
        setCondition(condition);
        setDate(date);
        setHigh(high);
        setLow(low);
        setText(text);
        setIconURLOFF(iconURLOFF);
        setTitle(title);

    }

    public ForecastModel ( int high, int low, String condition, String iconurl, String date ){
        setCondition(condition);
        setDate(date);
        setHigh(high);
        setLow(low);
        setIconurl(iconurl);

    }


    // Json deserialize method


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    //Method for JSON parsing
    public static ForecastModel forecastDeserialize(JSONObject jsonObject) throws JSONException {


//        String teXt = jsonObject.getString(text_key);
//        String title = jsonObject.getString("title");


        //Getting required Values
        String iconUrL = jsonObject.getString(icon_key);
        int hi = Integer.parseInt(jsonObject.getJSONObject(high_key).getString(celesius_key));
        int low = Integer.parseInt(jsonObject.getJSONObject(low_key).getString(celesius_key));
        String condition = jsonObject.getString(condition_key);
        String date = jsonObject.getJSONObject("date").getString(date_key);
        Log.e("DAAAAAAAAATE", date);


        //returning data to the fragment
        ForecastModel forecastModel = new ForecastModel(hi,low,condition,iconUrL,date);

        return forecastModel;

    }


//    Setters and getters for the model values
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }



    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }


    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }


    // Getting DB offline data by Forecast Provider




}
