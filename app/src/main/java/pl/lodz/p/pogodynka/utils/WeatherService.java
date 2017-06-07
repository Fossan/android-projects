package pl.lodz.p.pogodynka.utils;

import java.io.IOException;
import java.util.Map;

import pl.lodz.p.pogodynka.model.YahooWeather;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Pietro on 31.05.2017.
 */

public interface WeatherService {

    final String BASE_URL = "https://query.yahooapis.com";

    @GET("v1/public/yql")
    Call<YahooWeather> getWeather(@QueryMap Map<String, String> queryMap);

    class Factory {
        private static WeatherService weatherService;

        public static WeatherService getInstance() {
            if (weatherService == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                weatherService = retrofit.create(WeatherService.class);
                return weatherService;
            }
            else {
                return weatherService;
            }
        }

        public static YahooWeather getWeather(Map<String, String> queryMap) throws IOException {
            Call<YahooWeather> weather = WeatherService.Factory.getInstance().getWeather(queryMap);
            return weather.execute().body();
        }
    }
}
