package graduating.project.com.apm.model;

import java.util.ArrayList;

import graduating.project.com.apm.CommonFragment;
import graduating.project.com.apm.callback.MainResult;
import graduating.project.com.apm.callback.OnJsonToStaffsCompleted;
import graduating.project.com.apm.callback.OnJsonToTaskCompleted;
import graduating.project.com.apm.callback.OnJsonToTasksCompleted;
import graduating.project.com.apm.exclass.AsyncJsonTask;
import graduating.project.com.apm.exclass.JSONGetListStaffs;
import graduating.project.com.apm.exclass.JSONGetListTasks;
import graduating.project.com.apm.object.Task;

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

    public void convertJsonToListTasks(OnJsonToTasksCompleted listener, Object... objects){
        new JSONGetListTasks(listener).execute(objects);
    }

    public void convertJsonToListStaffs(OnJsonToStaffsCompleted listener, Object... objects){
        new JSONGetListStaffs(listener).execute(objects);
    }

    public void convertJsonToTask(OnJsonToTaskCompleted listener, Object... objects){
        new AsyncJsonTask(listener).execute(objects);
    }

    public ArrayList<CommonFragment> convertTasksToCommonFragment(ArrayList<Task> tasks){
        ArrayList<CommonFragment> fragments = new ArrayList<>();
        for(Task temp : tasks){
            fragments.add(new CommonFragment(temp));
        }
        return fragments;
    }

    public ArrayList<CommonFragment> getAllTasksToListCommonFragment(ArrayList<Task> tasks){
        ArrayList<CommonFragment> results = new ArrayList<>();
        for( Task task: tasks){
            results.add(new CommonFragment(task));
        }
        return results;
    }

    public ArrayList<CommonFragment> getAllTasksTypeToListCommonFragment(ArrayList<Task> tasks, int type){
        ArrayList<CommonFragment> results = new ArrayList<>();
        for( Task task: tasks){
            if(task.getType() == type)
                results.add(new CommonFragment(task));
        }
        return results;
    }

}
