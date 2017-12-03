package graduating.project.com.apm.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import graduating.project.com.apm.application.ApplicationController;
import graduating.project.com.apm.callback.RequestCallback;

/**
 * Created by Tuan on 17/10/2017.
 */

public class MakeRequest {
    private static String VOLLEY_TAG = "VOLLEY";

    public MakeRequest() {
    }

    /**
     *
     * @param url url.
     * @param method GET OR POST.
     * @param params Map key, value params.
     * @param callback Interface callback functions.
     */
    public static void makingRequest(String url, int method, HashMap<String,String> params, final RequestCallback callback) {
        JsonObjectRequest req = null;
        StringRequest sr = null;
        if (method == Request.Method.POST) {
            req = new JsonObjectRequest(url, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d(VOLLEY_TAG, response.toString(4));
                                if (callback != null) {
                                    callback.onPostSuccess(response); // call back function here
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(VOLLEY_TAG, String.valueOf(error.getMessage()));
                    if (callback != null) {
                        callback.onError(String.valueOf(error));
                    }
                }
            });
        } else {
            Log.d("request_AAA","3");
            if (params != null) {
                for (String key : params.keySet()) {
                    url = url.replace("{" + key + "}", params.get(key));
                }
            }
            Log.d("request_AAA", String.valueOf(url));
            sr = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            try {
                                Log.d(VOLLEY_TAG +"_succ", response);
                                JSONObject object = new JSONObject(response);
                                Boolean err = object.getBoolean("error");
                                if(!err)
                                    callback.onGetSuccess(new JSONArray(object.getString("content")));
                                else callback.onError(object.getString("content"));
                            } catch (JSONException e) {
                                Log.d(VOLLEY_TAG +"_err", String.valueOf(e.getMessage()));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(VOLLEY_TAG, String.valueOf(error.getMessage()));
                    if (callback != null) {
                        callback.onError(String.valueOf(error));
                    }
                }
            });

            // prepare the Request
//            req = new JsonObjectRequest(Request.Method.GET, url, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // display response
//                            try {
//                                Log.d(VOLLEY_TAG + "res", response.toString(4));
//                                if (callback != null) {
//                                    callback.onSuccess(response); // call back function here
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.e(VOLLEY_TAG + "error", String.valueOf(error.getMessage()));
//                            if (callback != null) {
//                                callback.onError(error);
//                            }
//                        }
//                    }
//            );
            Log.d(VOLLEY_TAG + "end", "1");
            ApplicationController.getInstance().addToRequestQueue(sr); // add the request object to the queue to be executed
            Log.d("request_AAA","4");
        }
    }
}
