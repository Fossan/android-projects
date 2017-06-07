package pl.lodz.p.pogodynka.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;
import pl.lodz.p.pogodynka.model.YahooWeather;

public class AdditionalFragment extends Fragment {

    private static EditText location;
    private static EditText sunrise;
    private static EditText sunset;
    private static EditText wind;
    private static EditText humidity;
    private static EditText visibility;

    private static YahooWeather yahooWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additional, container, false);

        location = (EditText) v.findViewById(R.id.location_additional);
        sunrise = (EditText) v.findViewById(R.id.sunrise);
        sunset = (EditText) v.findViewById(R.id.sunset);
        wind = (EditText) v.findViewById(R.id.wind);
        humidity = (EditText) v.findViewById(R.id.humidity);
        visibility = (EditText) v.findViewById(R.id.visibility);
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
        sunrise.setText("Sunrise: " + yahooWeather.getWeather().getAstronomy().getSunrise());
        sunset.setText("Sunset: " + yahooWeather.getWeather().getAstronomy().getSunset());
        wind.setText("Wind: " + yahooWeather.getWeather().getWind().getSpeed() + " " + yahooWeather.getQuery().getResults().getChannel().getUnits().getSpeed());
        humidity.setText("Humidity: " + yahooWeather.getWeather().getAtmosphere().getHumidity());
        visibility.setText("Visibility: " + yahooWeather.getWeather().getAtmosphere().getVisibility());
    }
}


