package graduating.project.com.apm.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.CommonFragment;
import graduating.project.com.apm.callback.MainResult;
import graduating.project.com.apm.callback.OnJsonToStaffsCompleted;
import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.callback.OnJsonToTasksCompleted;
import graduating.project.com.apm.model.MainHelper;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Staff;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.view.MainView;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainPresenter implements MainResult, OnJsonToTasksCompleted,OnJsonToTaskCompleted, OnJsonToStaffsCompleted {
    private MainView view;
    private MainHelper model;

    public MainPresenter(MainView view, MainHelper model){
        this.view = view;
        this.model = model;
        this.model.setOnMainResult(this);
    }


    public void getListTaskFromServer() {
//        List<Task> tasks = new ArrayList<>();
//        tasks.add(new Task(-1,"time",1,"temp1","temp1",23,"temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1",null,null));
//        tasks.add(new Task(2,"time",2,"temp1","temp1",23,"temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1","temp1",null, null));
//        view.fillTasksIntoViewPager(tasks);
//        view.updateIndicatorTv();
    }


    public void updateIndicatorTv(){
        view.updateIndicatorTv();
    }


    public void convertJsonToListTasks(Object... objects){
        Log.d("json_async_aaa","presenter1");
        model.convertJsonToListTasks(this,objects);
        Log.d("json_async_aaa","presenter2");
    }

    public void convertJsonToListStaffs(Object... objects){
        model.convertJsonToListStaffs(this,objects);
    }

    public void newAddedTask(Object... objects){
        model.convertJsonToTask(this,objects);
    }

    public void updateTask(Task task){
        view.updateTask(task);
    }

    public void updateStatusTask(int taskid, int status){
        view.updateStatusTask(taskid,status);
    }

    public void updateTypeTask(int taskid, int type) {
        view.updateTypeTask(taskid,type);
    }

    public void updateAssignTask(Assign assign) {
        view.updateAssignTask(assign);
    }

    public void newAddedIssue(Issue issue){
        view.addNewIssue(issue);
    }

    public void setAdapterForViewpager(ArrayList<CommonFragment> fragments) {
        view.setAdapterForViewPager(fragments);
    }

    public ArrayList<CommonFragment> getAllTasks(ArrayList<Task> tasks) {
        return model.getAllTasksToListCommonFragment(tasks);
    }

    public ArrayList<CommonFragment> getAllTasksType(ArrayList<Task> tasks, int type){
        return model.getAllTasksTypeToListCommonFragment(tasks, type);
    }

    public void setCurrentItemViewpager(int position){
        view.setCurrentItemViewpager(position);
    }

    public void updateActiveStaff(int taskid,int staffid, int process, int active) {
        view.updateActiveStaff(taskid,staffid,process,active);
    }

    @Override
    public void onGetTaskSucess(ArrayList<Task> tasks) {
        view.saveListTasks(tasks);
        view.fillTasksIntoViewPager(model.convertTasksToCommonFragment(tasks));
        view.updateIndicatorTv();
    }

    @Override
    public void onGetTaskError(String error) {
        view.showErrorLoadTask(error);
    }

    @Override
    public void onJsonToTasksCompleted(ArrayList<Task> tasks) {
        view.saveListTasks(tasks);
        view.fillTasksIntoViewPager(model.convertTasksToCommonFragment(tasks));
//        view.updateIndicatorTv();
    }

    @Override
    public void onJsonToTasksFailed(String error) {
        Log.d("json_async_aaa",error);
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

    @Override
    public void onJsonToStaffsCompleted(List<Staff> staffs) {
        view.addListStaffs(staffs);
    }

    @Override
    public void onJsonToStaffsFailed(String error) {
        Log.d("json_async_aaaliststaff","failed");
    }
}
