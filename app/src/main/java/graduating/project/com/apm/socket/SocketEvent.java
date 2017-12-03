package graduating.project.com.apm.socket;

import android.app.Activity;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;

import graduating.project.com.apm.presenter.MainPresenter;

/**
 * Created by Tuan on 29/11/2017.
 */

public class SocketEvent {

    private MainPresenter presenter;
    private Activity activity;
    private Emitter.Listener onNewTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Gson gson = new Gson();
//                    for (Object temp : args){
//                        JSONObject data = (JSONObject) temp;
//                        try {
//                            JSONArray array = new JSONArray(data.getString("content"));
//                            Log.d("socket_AAAA1",String.valueOf(array));
//                            for(int i=0; i < array.length(); i++) {
//                                JSONObject object = array.getJSONObject(i);
//                                Log.d("socket_AAAA2", String.valueOf(object));
//                                Task task = gson.fromJson(object.toString(), Task.class);
//                                Log.d("socket_AAAA3",String.valueOf(task.getId()));
//                            }
//                        } catch (JSONException e) {
//                            Log.d("socket_AAAA4",String.valueOf(e.getMessage()));
//                        }
//                    }
                    Log.d("json_async_aaa","socketevent1");
                    presenter.convertJsonToTasks(args);
                    Log.d("json_async_aaa","socketevent2");
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

    public Emitter.Listener getOnNewTask(){
        return onNewTask;
    }
}
