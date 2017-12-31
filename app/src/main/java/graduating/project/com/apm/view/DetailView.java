package graduating.project.com.apm.view;

import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 16/12/2017.
 */

public interface DetailView {

    void addNewIssue(Issue issue);

    void updateStatus(int taskid, int status);

    void updateAssignTask(Assign assign);

    void fillDetailTask();

    void updateTask(Task task);

    void updateTypeTask(int taskid, String type);

    void updateStatusTaskError();

}
