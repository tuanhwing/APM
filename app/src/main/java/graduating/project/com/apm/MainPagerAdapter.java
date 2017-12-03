package graduating.project.com.apm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] imageArray = {"assets://image6.jpg", "assets://image2.jpg", "assets://image3.jpg", "assets://image4.jpg", "assets://image5.jpg"};
    private List<CommonFragment> fragments = new ArrayList<>(); // Viewpager

    public MainPagerAdapter(FragmentManager fm, List<CommonFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        CommonFragment fragment = fragments.get(position % 10);
        fragment.bindData(imageArray[position % imageArray.length]);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
