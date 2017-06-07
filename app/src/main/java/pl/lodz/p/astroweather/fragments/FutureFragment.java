package pl.lodz.p.astroweather.fragments;

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

import org.w3c.dom.Text;

import java.io.IOException;

import pl.lodz.p.astroweather.R;
import pl.lodz.p.astroweather.model.Forecast;
import pl.lodz.p.astroweather.model.Image;
import pl.lodz.p.astroweather.model.YahooWeather;

public class FutureFragment extends Fragment {

    private static EditText location;
    private static TableLayout futureTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_future, container, false);
        location = (EditText) v.findViewById(R.id.location_future);
        futureTable = (TableLayout) v.findViewById(R.id.future_table);
        return v;
    }

    public static void updateLocation(YahooWeather weather) throws IOException {
        location.setText(weather.getWeather().getLocation().getCity());
        for (int i = 0; i < futureTable.getChildCount(); i++) {
            Forecast forecast = weather.getWeather().getItem().getForecast().get(i);
            TableRow row = (TableRow) futureTable.getChildAt(i);
            ImageView image = (ImageView) row.getChildAt(0);
            TextView date = (TextView) row.getChildAt(1);
            TextView temp = (TextView) row.getChildAt(2);

            date.setText(forecast.getDate());
            temp.setText(forecast.getLow() + " - " + forecast.getHigh() + "°" + weather.getWeather().getUnits().getTemperature());
        }
    }
}


