package graduating.project.com.apm.filter;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

import graduating.project.com.apm.CommonFragment;
import graduating.project.com.apm.MainActivity;
import graduating.project.com.apm.MainPagerAdapter;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.presenter.MainPresenter;

import static graduating.project.com.apm.MainActivity.tasks;

/**
 * Created by Tuan on 18/12/2017.
 */

public class TaskFilter extends Filter {

    private MainPagerAdapter mainPagerAdapter;
    private MainPresenter mainPresenter;
//    ArrayList<Task> filterTask;
    ArrayList<CommonFragment> filterTask;

    public TaskFilter(MainPagerAdapter mainPagerAdapter, ArrayList<CommonFragment> tasks, MainPresenter mainPresenter) {
        Log.d("search_filter","1");
        this.mainPagerAdapter = mainPagerAdapter;
        this.mainPresenter = mainPresenter;
        this.filterTask = tasks;
        Log.d("search_filter","2");
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        Log.d("search_filter","3");
        ArrayList<CommonFragment> filteredTask = new ArrayList<>();
        if(constraint != null  && constraint.length() > 0){
            Log.d("search_filter","4");
            //Change to upper
            constraint = constraint.toString().toLowerCase();
            //Store our filtered task
//            ArrayList<CommonFragment> filteredTask = new ArrayList<>();
            //            for(int i=0;i<filterTask.size();i++){
//                //check
//                try {
//                    int id = -1;
//                    if(filterTask.get(i).getTask().getId() == Integer.parseInt((String) constraint)){
//                        Log.d("search_filter", "searchvl " + String.valueOf(filterTask.get(i).getTask().getId()));
//                        filteredTask.add(filterTask.get(i));
//                    }
//                } catch (Exception e){
//                    Log.d("search_filter",String.valueOf(e.getMessage()));
//                }
//            }

            Log.d("search_filter","sizetask  " + String.valueOf(tasks.size()));
            for(Task t : tasks) {
                String id = String.valueOf(t.getId());
                if(id.toLowerCase().contains(constraint)){
                    filteredTask.add(new CommonFragment(t));
                } else {
                    for(Assign assign : t.getAssign()){
                        if(assign.getStaff().getName().contains(constraint)) {
                            filteredTask.add(new CommonFragment(t));
                        }
                    }
                }
//                try {
//                    int i = t.getId() / 10;
//                    if(i == Integer.parseInt((String) constraint)) filteredTask.add(t);
//                } catch (Exception e){
//                    Log.d("search_filter",String.valueOf(e.getMessage()));
//                }
            }

            filterResults.count = filteredTask.size();
            filterResults.values = filteredTask;
            Log.d("search_filter",String.valueOf(filteredTask));
            Log.d("search_filter","5");
        } else {
            Log.d("search_filter","6");
            for(Task t : MainActivity.tasks){
                filteredTask.add(new CommonFragment(t));
            }
            filterResults.count = filteredTask.size();
            filterResults.values = filteredTask;
            Log.d("search_filter","7");
        }
        Log.d("search_filter","8");
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
//        ArrayList<Task> temp = (ArrayList<Task>) results.values;
//        List<CommonFragment> fragments = new ArrayList<>();
//        for(int k = 0; k< temp.size(); k++){
//            fragments.add(new CommonFragment(temp.get(k)));
//        }
        Log.d("search_filter","9");
        mainPagerAdapter.setTasks((ArrayList<CommonFragment>) results.values);
        //refresh
        mainPagerAdapter.notifyDataSetChanged();
        mainPresenter.updateIndicatorTv();

//        mainPresenter.setAdapterForViewpager((ArrayList<CommonFragment>) results.values);

//        }
        Log.d("search_filter","10");
    }
}
