package graduating.project.com.apm.model;

import android.util.Log;

import graduating.project.com.apm.callback.MainResult;
import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.exclass.JSONGetTask;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainHelper {

    private MainResult result;

    public void setOnMainResult(MainResult result){
        this.result = result;
    }

    /**
     * Get all taks from server
     * @return
     */
    public void getListTaskFromServer(){

//        MakeRequest.makingRequest("http://10.69.225.76:8080/api/task", Request.Method.GET, null, new RequestCallback() {
//            @Override
//            public void onGetSuccess(JSONArray array) throws JSONException {
//                Gson gson = new Gson();
//                List<CommonFragment> tasks = new ArrayList<CommonFragment>();
//                for(int i=0; i < array.length(); i++) {
//                    JSONObject object = array.getJSONObject(i);
//                    Log.d("VOLLEY_call", String.valueOf(object));
//                    tasks.add(new CommonFragment(gson.fromJson(object.toString(), Task.class)));
//                }
//                result.onGetTaskSucess(tasks);
//            }
//
//            @Override
//            public void onPostSuccess(JSONObject response) {
//                Log.d("request_AAA",String.valueOf(response));
//            }
//
//            @Override
//            public void onError(String error) {
//                result.onGetTaskError(error);
//            }
//        });
//        for (int i = 0; i < 10; i++) {
//            // 预先准备10个fragment
//            results.add(new CommonFragment());
//        }
//        return results;
    }

    public void convertJsonToTasks(OnJsonToTaskCompleted listener, Object... objects){
        Log.d("json_async_aaa","mdel1");
        new JSONGetTask(listener).execute(objects);
        Log.d("json_async_aaa","mdel2");
    }

}
