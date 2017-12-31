package graduating.project.com.apm.presenter;

import org.json.JSONException;

import graduating.project.com.apm.callback.DetailResult;
import graduating.project.com.apm.model.DetailHelper;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.view.DetailView;

/**
 * Created by Tuan on 16/12/2017.
 */

public class DetailPresenter implements DetailResult {
    private DetailView view;
    private DetailHelper model;

    public DetailPresenter(DetailView view, DetailHelper model){
        this.view = view;
        this.model = model;
        this.model.setOnDetailResult(this);
    }

    public void newAddedIssue(Issue issue){
        view.addNewIssue(issue);
    }

    public void updateStatusTask(int taskid, int status){
        view.updateStatus(taskid, status);
    }

    public void sendNewIssueToServer(String content, int id) throws JSONException {
        model.sendNewIssueToServer(content, id);
    }

    public void updateNewAssign(Assign assign) {
        view.updateAssignTask(assign);
    }

    public void updateTask(Task task) {
        view.updateTask(task);
    }

    public void updateTypeTask(int taskid, String type) {
        view.updateTypeTask(taskid, type);
    }

    public void updateStatusTaskError(){
        view.updateStatusTaskError();
    }
}
