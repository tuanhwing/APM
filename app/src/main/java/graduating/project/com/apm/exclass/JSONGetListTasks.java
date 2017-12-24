package graduating.project.com.apm.exclass;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import graduating.project.com.apm.callback.OnJsonToTasksCompleted;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 30/11/2017.
 */

public class JSONGetListTasks extends AsyncTask<Object, Void, ArrayList<Task>> {
    private OnJsonToTasksCompleted listener;

    public JSONGetListTasks(OnJsonToTasksCompleted listener){
        this.listener = listener;
    }
    @Override
    protected ArrayList<Task> doInBackground(Object... params) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Gson gson = new Gson();
        for (Object temp : params){
            JSONObject data = (JSONObject) temp;
            try {
                JSONArray array = new JSONArray(data.getString("content"));
                Log.d("json_async_aaa",String.valueOf(array));
                for(int i=0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Log.d("json_async_aaa", String.valueOf(object));
                    tasks.add(gson.fromJson(object.toString(), Task.class));
                }
            } catch (JSONException e) {
                listener.onJsonToTasksFailed(e.getMessage());
            }
        }
        return tasks;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Task> tasks) {
        super.onPostExecute(tasks);
        listener.onJsonToTasksCompleted(tasks);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
