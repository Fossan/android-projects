package pl.lodz.p.pogodynka.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;

import pl.lodz.p.pogodynka.utils.QueryHelper;
import pl.lodz.p.pogodynka.R;
import pl.lodz.p.pogodynka.fragments.AdditionalFragment;
import pl.lodz.p.pogodynka.fragments.BasicFragment;
import pl.lodz.p.pogodynka.fragments.FutureFragment;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determineScreenType();
        context = this;
        final Thread network = QueryHelper.sendQuery("Lodz", "C", context);
        network.start();
        try {
            network.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_favorites:
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                QueryHelper.sendAndUpdate(context, QueryHelper.weatherData.getWeather().getLocation().getCity(), QueryHelper.weatherData.getWeather().getUnits().getTemperature());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void determineScreenType() {
        ViewPager pager;
        if ((pager = (ViewPager) findViewById(R.id.pager)) != null) {
            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), 3));
            pager.setOffscreenPageLimit(3);
        }
        else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.basic_fragment, new BasicFragment());
            fragmentTransaction.add(R.id.additional_fragment, new AdditionalFragment());
            fragmentTransaction.add(R.id.future_fragment, new FutureFragment());
            fragmentTransaction.commit();
        }
    }
}
