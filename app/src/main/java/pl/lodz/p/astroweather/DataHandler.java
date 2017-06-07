package pl.lodz.p.astroweather;


import java.io.Serializable;

import pl.lodz.p.astroweather.model.YahooWeather;

public class DataHandler implements Serializable {
    public static String location;
    public static String unit;
    public static YahooWeather weather;

}
