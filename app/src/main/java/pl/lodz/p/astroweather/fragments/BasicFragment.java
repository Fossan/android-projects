package pl.lodz.p.astroweather.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import pl.lodz.p.astroweather.R;
import pl.lodz.p.astroweather.model.YahooWeather;

public class BasicFragment extends Fragment {

    private static EditText location;
    private static EditText longitude;
    private static EditText latitude;
    private static EditText localTime;
    private static EditText temperature;
    private static EditText pressure;
    private static EditText description;
    private static ImageView image;
    private static Bitmap bitmap;

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
        image.setImageBitmap(bitmap);
        //updateLocationLabels();
        return v;
    }

    public static void updateLocation(YahooWeather weather, Bitmap bmp) throws IOException {
        location.setText(weather.getWeather().getLocation().getCity());
        longitude.setText(weather.getWeather().getItem().getLong());
        latitude.setText(weather.getWeather().getItem().getLat());
        localTime.setText(weather.getWeather().getLastBuildDate());
        temperature.setText(weather.getWeather().getItem().getCondition().getTemp() + "°" + weather.getQuery().getResults().getChannel().getUnits().getTemperature());
        pressure.setText(weather.getWeather().getAtmosphere().getPressure() + " " + weather.getQuery().getResults().getChannel().getUnits().getPressure());
        description.setText(weather.getWeather().getItem().getCondition().getText());
        bitmap = bmp;
        image.setImageBitmap(bitmap);
    }
}


