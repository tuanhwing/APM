package graduating.project.com.apm.exclass;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 30/11/2017.
 */

public class JSONGetTask extends AsyncTask<Object, Void, List<Task>> {
    private OnJsonToTaskCompleted listener;

    public JSONGetTask(OnJsonToTaskCompleted listener){
        this.listener = listener;
    }
    @Override
    protected List<Task> doInBackground(Object... params) {
        List<Task> tasks = new ArrayList<Task>();
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
                listener.onJsonToTaskFailed(e.getMessage());
            }
        }
        return tasks;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        listener.onJsonToTaskCompleted(tasks);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}