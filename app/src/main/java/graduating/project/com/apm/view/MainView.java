package graduating.project.com.apm.view;

import java.util.List;

import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Staff;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 19/11/2017.
 */

public interface MainView {
    void dealStatusBar(); //Fix height of statusbar

    void fillTasksIntoViewPager(List<Task> tasks);//fillViewPager

    void updateIndicatorTv();

    void showErrorLoadTask(String error);

    void addNewTaskIntoAdapter(Task task);

    void updateStatusTask(int taskid, int status);

    void updateAssignTask(int taskid, int status);

    void addListStaffs(List<Staff> staffs);

    void addNewIssue(Issue issue);

}
