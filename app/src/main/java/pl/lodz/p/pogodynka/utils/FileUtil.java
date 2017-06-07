package pl.lodz.p.pogodynka.utils;

import android.content.Context;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import pl.lodz.p.pogodynka.model.YahooWeather;

/**
 * Created by Pietro on 07.06.2017.
 */

public class FileUtil {

    public static void saveToJSON(Context context, YahooWeather weather) throws IOException {
        File file = new File(context.getFilesDir(), "weather.json");
        Gson gson = new Gson();
        String json = gson.toJson(weather);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.append(json);
            writer.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static YahooWeather readFromJSON(Context context) throws IOException {
        Gson gson = new Gson();
        String json = CharStreams.toString(new InputStreamReader(context.openFileInput("weather.json"), "UTF-8"));
        return gson.fromJson(json, YahooWeather.class);
    }
}
