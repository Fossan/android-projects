package pl.lodz.p.pogodynka.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.lodz.p.pogodynka.fragments.AdditionalFragment;
import pl.lodz.p.pogodynka.fragments.BasicFragment;
import pl.lodz.p.pogodynka.fragments.FutureFragment;
import pl.lodz.p.pogodynka.model.YahooWeather;

/**
 * Created by Pietro on 07.06.2017.
 */

public class QueryHelper {

    private static final String BASE_QUERY = "select * from weather.forecast UNIT woeid in (select woeid from geo.places(1) where text='LOCATION')";
    private static final String QUERY = "q";
    private static final String QUERY_FORMAT = "format";
    private static final String YAHOO_ICON = "http://l.yimg.com/a/i/us/we/52/CODE.gif";

    public static YahooWeather weatherData;
    public static Bitmap bmp;

    public static Map<String, String> getQueryMap(String city, String unit) {
        Map<String, String> queryMap = new LinkedHashMap<>();
        String firstQuery = BASE_QUERY.replace("LOCATION", city);
        String finalQuery = firstQuery.replace("UNIT", "WHERE u='" + unit + "' AND ");
        queryMap.put(QUERY, finalQuery);
        queryMap.put(QUERY_FORMAT, "json");
        return queryMap;
    }

    public static Bitmap getImage(String code) throws IOException {
        String url = YAHOO_ICON.replace("CODE", code);
        return BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
    }

    public static Thread sendQuery(final String city, final String unit, final Context context) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                weatherData = WeatherService.Factory.getWeather(QueryHelper.getQueryMap(city, unit));
                bmp = QueryHelper.getImage(weatherData.getWeather().getItem().getCondition().getCode());
            } catch (Exception e) {
                if (e instanceof UnknownHostException) {
                    try {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
                            }
                        });
                        weatherData = FileUtil.readFromJSON(context);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            }
        });
    }

    public static void update(final Context context) throws IOException, InterruptedException {
        try {
            BasicFragment.updateLocation(weatherData);
            AdditionalFragment.updateLocation(weatherData);
            FutureFragment.updateLocation(weatherData);
            FileUtil.saveToJSON(context, weatherData);
        } catch (final NullPointerException e) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    e.printStackTrace();
                    Toast.makeText(context, "Refreshing failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void sendAndUpdate(final Context context, String city, String unit) {
        final Thread network = QueryHelper.sendQuery(city, unit, context);
        network.start();
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    network.join();
                    QueryHelper.update(context);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}


