package graduating.project.com.apm.view;

import java.util.List;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 19/11/2017.
 */

public interface MainView {
    void dealStatusBar(); //Fix height of statusbar

    void fillTasksIntoViewPager(List<Task> tasks);//fillViewPager

    void updateIndicatorTv();

    void showErrorLoadTask(String error);
}
