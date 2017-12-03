package graduating.project.com.apm.callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tuan on 27/11/2017.
 */

public interface RequestCallback {

    void onGetSuccess(JSONArray array) throws JSONException;

    void onPostSuccess(JSONObject response);

    void onError(String error);
}
