package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import graduating.project.com.apm.R;
import graduating.project.com.apm.socket.SocketSingleton;

/**
 * Created by Tuan on 30/12/2017.
 */

public class PopupUpdateProcess extends Dialog implements View.OnClickListener {

    private Activity activity;
    private int taskid;
    private int typeTask;

    private TextView tvType;
    private  TextView tvUpdate;
    private TextView tvCancel;

    public PopupUpdateProcess(@NonNull Activity activity, int taskid, int typeTask) {
        super(activity);
        this.activity = activity;
        this.taskid = taskid;
        this.typeTask = typeTask;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_update_process);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        tvType = (TextView) findViewById(R.id.tv_type);
        tvUpdate = (TextView) findViewById(R.id.tv_update);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvType.setText("Current Process: " + getStringProcess(typeTask));
        tvUpdate.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    private String getStringProcess(int process){
        String pro = "none";
        switch (process){
            case 0: {
                pro="IN";
                break;
            }
            case 1: {
                pro="PHOTO";
                break;
            }
            case 2: {
                pro="BOOKBINDING";
                break;
            }
        }
        return pro;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update: {
                Log.d("error_update_type","asdasd1");
                JSONObject json = new JSONObject();
                try {
                    json.put("taskid", taskid);
                    json.put("type",typeTask);
                } catch (JSONException e) {
                    Log.d("error_update_type",String.valueOf(e.getMessage()));
                }
                SocketSingleton.getInstance().getSocket().emit("update-type-task", json);
                dismiss();
                break;
            }
        }
        dismiss();
    }

}
