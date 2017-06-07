package pl.lodz.p.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pl.lodz.p.astroweather.fragments.AdditionalFragment;
import pl.lodz.p.astroweather.fragments.BasicFragment;
import pl.lodz.p.astroweather.fragments.FutureFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        determineScreenType();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void determineScreenType() {
        ViewPager landScapePager;
        ViewPager portaitPager;
        if ((portaitPager = (ViewPager) findViewById(R.id.fragment_container)) != null) {
            portaitPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), 3));
        } else if ((landScapePager = (ViewPager) findViewById(R.id.pager)) != null) {
            landScapePager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), 3));
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.basic_fragment, new BasicFragment());
            fragmentTransaction.add(R.id.additional_fragment, new AdditionalFragment());
            fragmentTransaction.add(R.id.future_fragment, new FutureFragment());
            fragmentTransaction.commit();
        }

    }
}
