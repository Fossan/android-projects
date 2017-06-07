package pl.lodz.p.astroweather.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import pl.lodz.p.astroweather.R;
import pl.lodz.p.astroweather.model.YahooWeather;

public class AdditionalFragment extends Fragment {

    private static EditText location;
    private static EditText sunrise;
    private static EditText sunset;
    private static EditText wind;
    private static EditText humidity;
    private static EditText visibility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_additional, container, false);

        location = (EditText) v.findViewById(R.id.location_additional);
        sunrise = (EditText) v.findViewById(R.id.sunrise);
        sunset = (EditText) v.findViewById(R.id.sunset);
        wind = (EditText) v.findViewById(R.id.wind);
        humidity = (EditText) v.findViewById(R.id.humidity);
        visibility = (EditText) v.findViewById(R.id.visibility);
        //updateLocationLabels();
        return v;
    }

    public static void updateLocation(YahooWeather weather) throws IOException {
        location.setText(weather.getWeather().getLocation().getCity());
        sunrise.setText("Sunrise: " + weather.getWeather().getAstronomy().getSunrise());
        sunset.setText("Sunset: " + weather.getWeather().getAstronomy().getSunset());
        wind.setText("Wind: " + weather.getWeather().getWind().getSpeed() + " " + weather.getQuery().getResults().getChannel().getUnits().getSpeed());
        humidity.setText("Humidity: " + weather.getWeather().getAtmosphere().getHumidity());
        visibility.setText("Visibility: " + weather.getWeather().getAtmosphere().getVisibility());
    }
}


