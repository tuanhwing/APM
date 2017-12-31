package graduating.project.com.apm.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.presenter.MainPresenter;

/**
 * Created by Tuan on 29/11/2017.
 */

public class SocketEvent {

    private MainPresenter presenter;
    private Activity activity;

    private Emitter.Listener onListTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("log_task_AAAAlist",String.valueOf(args[0]));
                    presenter.convertJsonToListTasks(args);
                }
            });
        }
    };

    private Emitter.Listener onConnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("log_task_AAAA","connected");
                    SocketSingleton.getInstance().getSocket().emit("get-list-task");
                    SocketSingleton.getInstance().getSocket().emit("get-list-staff");
                }
            });
        }
    };

    private Emitter.Listener onNewTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("log_task_AAAAlist_n",String.valueOf(args[0]));
                    presenter.newAddedTask(args);
                }
            });
        }
    };

    private Emitter.Listener onUpdateTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    for (Object temp : args){
                        JSONObject data = (JSONObject) temp;
                        try {
                            presenter.updateTask(gson.fromJson(data.getString("content"),Task.class));
                        } catch (JSONException e) {
                            Log.e("error_edit_task",String.valueOf(e.getMessage()));
                        }
                    }
                }
            });
        }
    };


    private Emitter.Listener onUpdateStatusTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        int taskid = data.getInt("taskid");
                        int status = data.getInt("status");
                        presenter.updateStatusTask(taskid, status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(activity,String.valueOf(args[0]),Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onUpadteTypeTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        int taskid = data.getInt("taskid");
                        String type = data.getString("type");
                        presenter.updateTypeTask(taskid, type);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(activity,String.valueOf(args[0]),Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onAssignTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("lol_assign_aaaaa",String.valueOf(args[0]));
                    Gson gson = new Gson();
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Boolean err = data.getBoolean("error");
                        if(!err){
                            presenter.updateAssignTask(gson.fromJson(data.getString("content"),Assign.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onListStaffs = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("log_task_AAAAlist",String.valueOf(args[0]));
                    presenter.convertJsonToListStaffs(args);
                    Toast.makeText(activity, String.valueOf(args[0]), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onNewIssue = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("log_task_AAAAissue",String.valueOf(args[0]));
                    Gson gson = new Gson();
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Boolean err = data.getBoolean("error");
                        if(!err){
                            presenter.newAddedIssue(gson.fromJson(data.getString("content"),Issue.class));
                        }
                        Toast.makeText(activity, String.valueOf(args[0]), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    

    public SocketEvent(){
    }

    public SocketEvent(Activity activity, MainPresenter presenter){
        this.activity = activity;
        this.presenter = presenter;
    }

    public Emitter.Listener getOnListTask(){
        return onListTask;
    }

    public Emitter.Listener getOnNewTask(){
        return onNewTask;
    }

    public Emitter.Listener getOnConnected() { return onConnected; }

    public Emitter.Listener getOnUpdateStatusTask() {
        return onUpdateStatusTask;
    }

    public  Emitter.Listener getOnAssignTask(){
        return onAssignTask;
    }

    public Emitter.Listener getOnListStaffs() { return onListStaffs; }

    public Emitter.Listener getOnNewIssue() { return onNewIssue; }

    public Emitter.Listener getOnUpdateTask() { return onUpdateTask; }

    public Emitter.Listener getGetOnUpadteTypeTask() { return onUpadteTypeTask; }
}
