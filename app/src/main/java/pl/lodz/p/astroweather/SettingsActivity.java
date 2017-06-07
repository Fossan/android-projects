package pl.lodz.p.astroweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.lodz.p.astroweather.fragments.AdditionalFragment;
import pl.lodz.p.astroweather.fragments.BasicFragment;
import pl.lodz.p.astroweather.fragments.FutureFragment;
import pl.lodz.p.astroweather.model.YahooWeather;


public class SettingsActivity extends AppCompatActivity {

    String BASE_QUERY = "select * from weather.forecast UNIT woeid in (select woeid from geo.places(1) where text='LOCATION')";
    String QUERY = "q";
    String QUERY_FORMAT = "format";
    String YAHOO_ICON = "http://l.yimg.com/a/i/us/we/52/CODE.gif";

    DatabaseHelper dbHelper;
    YahooWeather weatherData;
    Bitmap bmp;

    private static EditText location;
    private static RadioGroup radioGroup;
    private Button ok;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dbHelper = new DatabaseHelper(this);
        initalizeComponents();
        setButtonActions();
    }

    private synchronized void initalizeComponents() {
        location = (EditText) findViewById(R.id.locationValue);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);
    }

    private void setButtonActions() {
        setCancelButtonAction();
        setOkButtonAction();
    }

    private void setOkButtonAction() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, String> queryMap = new LinkedHashMap<>();
                            String firstQuery = BASE_QUERY.replace("LOCATION", location.getText().toString());
                            String finalQuery = firstQuery.replace("UNIT", "WHERE u='" + ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString() + "' AND ");
                            queryMap.put(QUERY, finalQuery);
                            queryMap.put(QUERY_FORMAT, "json");
                            weatherData = WeatherService.Factory.getWeather(queryMap);
                            String url = YAHOO_ICON.replace("CODE", weatherData.getWeather().getItem().getCondition().getCode());
                            bmp = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());

                        } catch (Exception e) {
                            e.printStackTrace();
                            //showMessage("Something went wrong");
                        }
                    }
                });
                thread.start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread.join();
                            BasicFragment.updateLocation(weatherData, bmp);
                            AdditionalFragment.updateLocation(weatherData);
                            FutureFragment.updateLocation(weatherData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                finish();
            }
        });
    }

    private void showMessage(String message) {

        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    private void setCancelButtonAction() {

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
