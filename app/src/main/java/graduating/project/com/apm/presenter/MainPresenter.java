package graduating.project.com.apm.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.callback.MainResult;
import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.callback.OnJsonToTasksCompleted;
import graduating.project.com.apm.model.MainHelper;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.view.MainView;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainPresenter implements MainResult, OnJsonToTasksCompleted,OnJsonToTaskCompleted {
    private MainView view;
    private MainHelper model;

    public MainPresenter(MainView view, MainHelper model){
        this.view = view;
        this.model = model;
        this.model.setOnMainResult(this);
    }

    public void getListTaskFromServer() {
        List<Task> tasks = new ArrayList<>();
//        tasks.add(new Task(1,"time",1,"temp1","temp1",23,"temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1",null,null));
//        tasks.add(new Task(2,"time",2,"temp1","temp1",23,"temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1",null, null));
        view.fillTasksIntoViewPager(tasks);
        view.updateIndicatorTv();
    }

    public void convertJsonToTasks(Object... objects){
        Log.d("json_async_aaa","presenter1");
        model.convertJsonToTasks(this,objects);
        Log.d("json_async_aaa","presenter2");
    }

    public void newAddedTask(Object... objects){
        model.convertJsonToTask(this,objects);
    }
    @Override
    public void onGetTaskSucess(List<Task> tasks) {
        view.fillTasksIntoViewPager(tasks);
        view.updateIndicatorTv();
    }

    @Override
    public void onGetTaskError(String error) {
        view.showErrorLoadTask(error);
    }

    @Override
    public void onJsonToTasksCompleted(List<Task> tasks) {
//        view.fillTasksIntoViewPager(tasks);
//        view.updateIndicatorTv();
        Log.d("json_async_aaa","completed");
    }

    @Override
    public void onJsonToTasksFailed(String error) {
        Log.d("json_async_aaa","failed");
    }

    @Override
    public void onJsonToTaskCompleted(Task task) {
        view.addNewTaskIntoAdapter(task);
        view.updateIndicatorTv();
    }

    @Override
    public void onJsonToTaskFailed(String error) {
        Log.d("json_async_aaatask","failed");
    }
}
