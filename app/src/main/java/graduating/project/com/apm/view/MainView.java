package graduating.project.com.apm.view;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.CommonFragment;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Staff;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 19/11/2017.
 */

public interface MainView {
    void dealStatusBar(); //Fix height of statusbar

    void saveListTasks(ArrayList<Task> tasks);

    void fillTasksIntoViewPager(ArrayList<CommonFragment> fragments);//fillViewPager

    void setAdapterForViewPager(ArrayList<CommonFragment> fragments);

    void updateIndicatorTv();

    void showErrorLoadTask(String error);

    void addNewTaskIntoAdapter(Task task);

    void updateTask(Task task);

    void updateStatusTask(int taskid, int status);

    void updateAssignTask(Assign assign);

    void addListStaffs(List<Staff> staffs);

    void addNewIssue(Issue issue);

    void setCurrentItemViewpager(int position);


}
