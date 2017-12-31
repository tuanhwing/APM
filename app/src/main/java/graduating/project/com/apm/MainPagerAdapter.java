package graduating.project.com.apm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import graduating.project.com.apm.filter.TaskFilter;
import graduating.project.com.apm.presenter.MainPresenter;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter implements Filterable{

    private final String[] imageArray = {"assets://image6.jpg", "assets://image2.jpg", "assets://image3.jpg", "assets://image4.jpg", "assets://image5.jpg"};
    private ArrayList<CommonFragment> fragments = new ArrayList<>(); // Viewpager
    private ArrayList<CommonFragment> filterList = new ArrayList<>();
    private MainPresenter mainPresenter;
    private TaskFilter taskFilter;

    public MainPagerAdapter(FragmentManager fm, ArrayList<CommonFragment> fragments, MainPresenter mainPresenter) {
        super(fm);
        this.mainPresenter = mainPresenter;
        this.fragments = fragments;
        this.filterList = fragments;
    }

    public void setTasks(ArrayList<CommonFragment> tasks){
        fragments = new ArrayList<>();
        for (CommonFragment temp : tasks){
            Log.d("search_adapter",String.valueOf(temp));
            fragments.add(temp);
        }
//        mainPresenter.setCurrentItemViewpager(0);
        filterList = fragments;
    }

    public ArrayList<CommonFragment> getFilterList(){
        return filterList;
    }

    @Override
    public Fragment getItem(int position) {
        CommonFragment fragment = fragments.get(position);
        fragment.bindData(imageArray[0]);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Filter getFilter() {
        Log.d("search_adapter","1");
        if(taskFilter == null){
            Log.d("search_adapter","2");
            taskFilter = new TaskFilter(this, filterList, mainPresenter);
        }
        return taskFilter;
    }

    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object){
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }
}
