package pl.lodz.p.pogodynka.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import pl.lodz.p.pogodynka.utils.DatabaseHelper;
import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;


public class SettingsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private static EditText location;
    private static RadioGroup radioGroup;
    private static CheckBox isFavorite;
    private Button ok;
    private Button cancel;

    static Context context;

    private static String city;
    private static int unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        dbHelper = new DatabaseHelper(this);
        initalizeComponents();
        setButtonActions();
        if (QueryHelper.weatherData.getQuery().getResults() != null) {
            city = QueryHelper.weatherData.getWeather().getLocation().getCity();
            unit = QueryHelper.weatherData.getWeather().getUnits().getTemperature().equalsIgnoreCase("c") ? R.id.radio_celsius : R.id.radio_fahrenheit;
        }
        location.setText(city);
        radioGroup.check(unit);
    }

    private synchronized void initalizeComponents() {
        location = (EditText) findViewById(R.id.locationValue);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        isFavorite = (CheckBox) findViewById(R.id.favorite_checkbox);
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
                if (location.getText() == null || radioGroup.getCheckedRadioButtonId() == -1) {
                    return;
                }
                QueryHelper.sendAndUpdate(context, location.getText().toString(), ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());

                if (isFavorite.isChecked() && QueryHelper.weatherData.getQuery().getResults() != null) {
                    dbHelper.addFavorite(QueryHelper.weatherData.getWeather().getLocation().getCity(), QueryHelper.weatherData.getWeather().getUnits().getTemperature());
                }
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
