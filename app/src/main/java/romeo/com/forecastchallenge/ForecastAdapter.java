package romeo.com.forecastchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastModel> list = new ArrayList<>();

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView condition, hiLow, text, date;
        ImageView icon;

        public ForecastViewHolder(View itemView) {
            super(itemView);

            condition = (TextView) itemView.findViewById(R.id.weatherCondition);
            hiLow = (TextView) itemView.findViewById(R.id.hi_Low);
            text = (TextView) itemView.findViewById(R.id.forecast);
            date = (TextView) itemView.findViewById(R.id.date);
            icon = (ImageView) itemView.findViewById(R.id.weatherIcon);

        }

    }

    Context c;

    public ForecastAdapter(List<ForecastModel> forecastModels, Context c) {
        this.list = forecastModels;
        this.c = c;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weatherdaycard, parent, false);

        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        ForecastModel forecastModel = list.get(position);
        holder.text.setText("Forecast Details:" + "\n" + forecastModel.getText());
        holder.condition.setText(forecastModel.getCondition());
        holder.date.setText("Measure date:" + "\n" + forecastModel.getTitle() + "," + forecastModel.getDate());
        holder.hiLow.setText(forecastModel.getHigh() + "/" + forecastModel.getLow());
        Toast.makeText(c,forecastModel.getIconURLOFF(),Toast.LENGTH_LONG).show();
        if (Check()) {
            Picasso.with(c).load(forecastModel.getIconurl()).into(holder.icon);
        } else {
            Picasso.with(c).load(Environment.getExternalStorageDirectory().getPath() + "/" + forecastModel.getCondition() + ".gif").into(holder.icon);
        }

    }

    public Boolean Check() {
        ConnectivityManager cn = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {
            Toast.makeText(c, "No internet connection.!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

    }

        @Override
        public int getItemCount () {
            return list.size();
        }
    }

