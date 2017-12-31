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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.socket.SocketSingleton;

/**
 * Created by Tuan on 30/12/2017.
 */

public class PopupUpdateProcess extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private int taskid;
    private String typeTask;
    private String typeTemp="IN";

    private TextView tvType;
    private Spinner spinner;
    private  TextView tvUpdate;
    private TextView tvCancel;

    public PopupUpdateProcess(@NonNull Activity activity, int taskid, String typeTask) {
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

        spinner = (Spinner) findViewById(R.id.spinner);
        tvType.setText("Current Process: " + typeTask);
        List<String> temps = new ArrayList<>();
        temps.add("IN");
        temps.add("PHOTO");
        temps.add("BOOKBINDING");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, temps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        tvUpdate.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update: {
                Log.d("error_update_type","asdasd1");
                if(!typeTask.equals(typeTemp)){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("taskid", taskid);
                        json.put("type",typeTemp);
                    } catch (JSONException e) {
                        Log.d("error_update_type",String.valueOf(e.getMessage()));
                    }
                    SocketSingleton.getInstance().getSocket().emit("update-type-task", json);
                    dismiss();
                    Log.d("error_update_type","asdasd2");
                }
                break;
            }
        }
        dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                typeTemp = "IN";
                break;
            case 1:
                typeTemp = "PHOTO";
                break;
            case 2:
                typeTemp = "BOOKBINDING";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
