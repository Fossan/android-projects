package pl.lodz.p.pogodynka.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;

import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;
import pl.lodz.p.pogodynka.model.Forecast;
import pl.lodz.p.pogodynka.model.YahooWeather;

public class FutureFragment extends Fragment {

    private static EditText location;
    private static TableLayout futureTable;

    private static YahooWeather yahooWeather;
    private static Bitmap[] bitmaps;
    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_future, container, false);
        location = (EditText) v.findViewById(R.id.location_future);
        futureTable = (TableLayout) v.findViewById(R.id.future_table);
        context = getContext();
        try {
            if (QueryHelper.weatherData != null) {
                yahooWeather = QueryHelper.weatherData;
                updateInfo();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static void updateLocation(YahooWeather weather) throws IOException, InterruptedException {
        yahooWeather = weather;
        updateInfo();
    }

    private static void updateInfo() throws InterruptedException {
        location.setText(yahooWeather.getWeather().getLocation().getCity());
        for (int i = 0; i < futureTable.getChildCount(); i++) {
            final Forecast forecast = yahooWeather.getWeather().getItem().getForecast().get(i);
            TableRow row = (TableRow) futureTable.getChildAt(i);

            final ImageView image = (ImageView) row.getChildAt(0);
            TextView date = (TextView) row.getChildAt(1);
            TextView temp = (TextView) row.getChildAt(2);

            date.setText(forecast.getDay() + ", " + forecast.getDate());
            temp.setText(forecast.getLow() + " - " + forecast.getHigh() + "°" + yahooWeather.getWeather().getUnits().getTemperature());
            image.setImageResource(context.getResources().getIdentifier("icon_" + forecast.getCode(), "drawable", context.getPackageName()));

        }
    }
}


