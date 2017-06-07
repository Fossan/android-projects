package pl.lodz.p.pogodynka.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;
import pl.lodz.p.pogodynka.model.YahooWeather;

public class BasicFragment extends Fragment {

    private static EditText location;
    private static EditText longitude;
    private static EditText latitude;
    private static EditText localTime;
    private static EditText temperature;
    private static EditText pressure;
    private static EditText description;
    private static ImageView image;
    private static YahooWeather yahooWeather;

    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_basic, container, false);

        location = (EditText) v.findViewById(R.id.location);
        longitude = (EditText) v.findViewById(R.id.longitude);
        latitude = (EditText) v.findViewById(R.id.latitude);
        localTime = (EditText) v.findViewById(R.id.time);
        temperature = (EditText) v.findViewById(R.id.temperature);
        pressure = (EditText) v.findViewById(R.id.pressure);
        description = (EditText) v.findViewById(R.id.description);
        image = (ImageView) v.findViewById(R.id.imageView);
        context = getContext();
        if (QueryHelper.weatherData != null) {
            yahooWeather = QueryHelper.weatherData;
            updateInfo();
        }
        return v;
    }

    public static void updateLocation(YahooWeather weather) throws IOException {
        yahooWeather = weather;
        updateInfo();
    }

    private static void updateInfo() {
        location.setText(yahooWeather.getWeather().getLocation().getCity());
        longitude.setText(yahooWeather.getWeather().getItem().getLong());
        latitude.setText(yahooWeather.getWeather().getItem().getLat());
        localTime.setText(yahooWeather.getWeather().getLastBuildDate());
        temperature.setText(yahooWeather.getWeather().getItem().getCondition().getTemp() + "°" + yahooWeather.getQuery().getResults().getChannel().getUnits().getTemperature());
        pressure.setText(yahooWeather.getWeather().getAtmosphere().getPressure() + " " + yahooWeather.getQuery().getResults().getChannel().getUnits().getPressure());
        description.setText(yahooWeather.getWeather().getItem().getCondition().getText());
        image.setImageResource(context.getResources().getIdentifier("icon_" + yahooWeather.getWeather().getItem().getCondition().getCode(), "drawable", context.getPackageName()));
    }
}


