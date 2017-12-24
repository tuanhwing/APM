package graduating.project.com.apm.view;

import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;

/**
 * Created by Tuan on 16/12/2017.
 */

public interface DetailView {

    void addNewIssue(Issue issue);

    void updateStatus(int taskid, int status);

    void updateAssignTask(Assign assign);

}
