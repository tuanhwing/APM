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
import graduating.project.com.apm.presenter.DetailPresenter;

/**
 * Created by Tuan on 16/12/2017.
 */

public class SocketEventDetail {

    private DetailPresenter presenter;
    private Activity activity;

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

    private Emitter.Listener onUpdateTask  = new Emitter.Listener() {
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

    private Emitter.Listener onUpdateStatus = new Emitter.Listener() {
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
                    Toast.makeText(activity, "update status", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onUpadteType = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        int taskid = data.getInt("taskid");
                        int type = data.getInt("type");
                        presenter.updateTypeTask(taskid, type);
                    } catch (JSONException e) {
                        Log.e("error_update_type",String.valueOf(e.getMessage()));
                    }
                    Toast.makeText(activity, "update type", Toast.LENGTH_LONG).show();
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
                    Gson gson = new Gson();
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Boolean err = data.getBoolean("error");
                        if(!err){
                            presenter.updateNewAssign(gson.fromJson(data.getString("content"),Assign.class));
                        }

                        Toast.makeText(activity,"DETAIL " + String.valueOf(args[0]),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onResultAssign = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Boolean err = data.getBoolean("error");
                        if(err){
                            Toast.makeText(activity,String.valueOf(data.getString("message")),Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onErrorUpdateStatusTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        presenter.updateStatusTaskError();
                        Toast.makeText(activity,String.valueOf(data.getString("message")),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onErrorUpdateTypeTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Toast.makeText(activity,String.valueOf(data.getString("message")),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    private Emitter.Listener onActiveStaff = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        int taskid = data.getInt("taskid");
                        int staffid = data.getInt("staffid");
                        int process = data.getInt("type");
                        int active = data.getInt("active");
                        presenter.updateActiveStaff(taskid,staffid,process,active);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onErrorActiveStaff = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        Toast.makeText(activity,String.valueOf(data.getString("message")),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public SocketEventDetail(){
    }

    public SocketEventDetail(Activity activity, DetailPresenter presenter){
        this.activity = activity;
        this.presenter = presenter;
    }

    public Emitter.Listener getOnNewIssue() {
        return onNewIssue;
    }

    public Emitter.Listener getOnUpdateStatus() {
        return onUpdateStatus;
    }

    public Emitter.Listener getOnAssignTask(){
        return onAssignTask;
    }

    public Emitter.Listener getOnResultAssign() { return onResultAssign; }

    public Emitter.Listener getOnErrorUpdateStatusTask() { return onErrorUpdateStatusTask; }

    public Emitter.Listener getOnUpdateTask() { return onUpdateTask; }

    public Emitter.Listener getOnUpadteType() { return onUpadteType; }

    public Emitter.Listener getOnErrorUpdateTypeTask() { return onErrorUpdateTypeTask; }

    public Emitter.Listener getOnActiveStaff() { return onActiveStaff;}

    public Emitter.Listener getOnErrorActiveStaff() { return onErrorActiveStaff; }
}
