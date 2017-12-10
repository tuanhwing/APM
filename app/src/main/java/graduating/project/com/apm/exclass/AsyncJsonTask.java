package graduating.project.com.apm.exclass;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 10/12/2017.
 */

public class AsyncJsonTask extends AsyncTask<Object, Void, Task> {
    private OnJsonToTaskCompleted listener;

    public AsyncJsonTask(OnJsonToTaskCompleted listener){
        this.listener = listener;
    }
    @Override
    protected Task doInBackground(Object... params) {
        Task task = null;
        Gson gson = new Gson();
        for (Object temp : params){
            JSONObject data = (JSONObject) temp;
            try {
                task = gson.fromJson(data.getString("content"),Task.class);
            } catch (JSONException e) {
                listener.onJsonToTaskFailed(e.getMessage());
            }
        }
        return task;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Task task) {
        super.onPostExecute(task);
        listener.onJsonToTaskCompleted(task);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
