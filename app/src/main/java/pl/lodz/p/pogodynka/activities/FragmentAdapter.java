package pl.lodz.p.pogodynka.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pl.lodz.p.pogodynka.fragments.AdditionalFragment;
import pl.lodz.p.pogodynka.fragments.BasicFragment;
import pl.lodz.p.pogodynka.fragments.FutureFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private String titles[] = {"Basic", "Additional", "Future"};

    public FragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BasicFragment();
            case 1:
                return new AdditionalFragment();
            case 2:
                return new FutureFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}