package graduating.project.com.apm.exclass;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.callback.OnJsonToStaffsCompleted;
import graduating.project.com.apm.object.Staff;

/**
 * Created by Tuan on 12/12/2017.
 */

public class JSONGetListStaffs extends AsyncTask<Object, Void, List<Staff>> {

    private OnJsonToStaffsCompleted listener;

    public JSONGetListStaffs(OnJsonToStaffsCompleted listener){
        this.listener = listener;
    }
    @Override
    protected List<Staff> doInBackground(Object... params) {
        List<Staff> staffs = new ArrayList<>();
        Gson gson = new Gson();
        for (Object temp : params){
            try {
                JSONArray array = new JSONArray(temp.toString());
                Log.d("json_async_aaa",String.valueOf(array));
                for(int i=0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Log.d("json_async_aaa", String.valueOf(object));
                    staffs.add(gson.fromJson(object.toString(), Staff.class));
                }
            } catch (JSONException e) {
                listener.onJsonToStaffsFailed(e.getMessage());
            }
        }
        return staffs;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Staff> staffs) {
        super.onPostExecute(staffs);
        listener.onJsonToStaffsCompleted(staffs);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
