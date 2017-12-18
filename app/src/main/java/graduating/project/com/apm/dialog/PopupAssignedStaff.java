package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Staff;
import graduating.project.com.apm.socket.SocketSingleton;

/**
 * Created by Tuan on 06/12/2017.
 */

public class PopupAssignedStaff extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private List<Staff> staffs;
    private List<Assign> assigns;
    private int staffid;
    private int taskid;

    private TextView tvAssign;
    private Button btnAssign;
    private Spinner spinner;

    public PopupAssignedStaff(Activity activity, List<Staff> staffs, List<Assign> assigns, int taskid) {
        super(activity);
        this.activity = activity;
        this.staffs = staffs;
        this.assigns = assigns;
        this.taskid = taskid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_assgin);

        tvAssign = (TextView) findViewById(R.id.tv_assign);
        btnAssign = (Button) findViewById(R.id.btn_assign);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> temps = new ArrayList<>();
        for(int i =0; i< staffs.size(); i++){
            temps.add(staffs.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, temps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for (int i = 0; i < assigns.size(); i++)
            tvAssign.append(assigns.get(i).getStaff().getName());
        spinner.setOnItemSelectedListener(this);
        btnAssign.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_assign: {
                JSONObject json = new JSONObject();
                try {
                    json.put("taskid",taskid);
                    json.put("staffid",staffid);
                } catch (JSONException e) {
                    Log.d("assign-staff-for-task",String.valueOf(e.getMessage()));
                }
                SocketSingleton.getInstance().getSocket().emit("assign-staff-for-task", json);
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        staffid = staffs.get(position).getId();
        switch (position) {
            case 0:
                Toast.makeText(activity, "0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}