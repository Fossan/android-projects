package pl.lodz.p.pogodynka.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.lodz.p.pogodynka.utils.DatabaseHelper;
import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;

/**
 * Created by Pietro on 06.06.2017.
 */

public class FavoriteActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private static Spinner dropdown;
    private Button ok;
    private Button cancel;

    private Map<String, String> locationMap;

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        context = this;
        dbHelper = new DatabaseHelper(this);
        initalizeComponents();
    }

    private void initializeSpinner() {
        dropdown = (Spinner) findViewById(R.id.fav_spinner);
        Cursor cursor = dbHelper.getFavorites();
        locationMap = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            locationMap.put(cursor.getString(1), cursor.getString(2)); //city, unit
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locationMap.keySet().toArray(new String[cursor.getCount()]));
        dropdown.setAdapter(adapter);
    }

    private void setButtonActions() {
        setOkButtonAction();
        setCancelButtonAction();
    }

    private void initalizeComponents() {
        initializeSpinner();
        ok = (Button) findViewById(R.id.fav_ok);
        cancel = (Button) findViewById(R.id.fav_cancel);
        setButtonActions();
    }

    private void setOkButtonAction() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dropdown.getSelectedItem() == null) {
                    return;
                }
                QueryHelper.sendAndUpdate(context, dropdown.getSelectedItem().toString(), locationMap.get(dropdown.getSelectedItem().toString()));
                finish();
            }
        });
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
