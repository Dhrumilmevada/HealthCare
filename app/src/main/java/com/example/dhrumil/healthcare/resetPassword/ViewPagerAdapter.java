package com.example.dhrumil.healthcare.resetPassword;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.example.dhrumil.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 2/14/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> view_pager_content = new ArrayList<>();
    private ArrayList<String> tab_title = new ArrayList<>();
    private FragmentManager fm;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void addToViewPager(Fragment fragment, String title)
    {
        view_pager_content.add(fragment);
        tab_title.add(title);
    }


    @Override
    public Fragment getItem(int position) {
        return view_pager_content.get(position);
    }

    @Override
    public int getCount() {
        return view_pager_content.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
