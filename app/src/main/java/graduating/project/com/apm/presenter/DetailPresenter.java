package graduating.project.com.apm.presenter;

import graduating.project.com.apm.callback.DetailResult;
import graduating.project.com.apm.model.DetailHelper;
import graduating.project.com.apm.object.Issue;
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
}
