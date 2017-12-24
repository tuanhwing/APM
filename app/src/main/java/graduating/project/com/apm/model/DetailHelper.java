package graduating.project.com.apm.model;

import org.json.JSONException;
import org.json.JSONObject;

import graduating.project.com.apm.callback.DetailResult;
import graduating.project.com.apm.socket.SocketSingleton;

/**
 * Created by Tuan on 16/12/2017.
 */

public class DetailHelper {

    private DetailResult result;

    public void setOnDetailResult(DetailResult result){
        this.result = result;
    }

    public void sendNewIssueToServer(String content, int id) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("taskid",id);
        object.put("message",content);
        SocketSingleton.getInstance().getSocket().emit("add-issue",object);
    }
}
