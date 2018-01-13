package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.exclass.MyDate;
import graduating.project.com.apm.exclass.SwitchChangedActive;
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
    private int process;

    private Switch swActiveM;
    private TextView tvAssign;
    private TextView tvCancel;
    private LinearLayout listAssign;
    private Spinner spinner;

    public PopupAssignedStaff(Activity activity, List<Staff> staffs, List<Assign> assigns, int taskid, int process) {
        super(activity);
        this.activity = activity;
        this.staffs = staffs;
        this.assigns = assigns;
        this.taskid = taskid;
        this.process = process;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_assgin);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        tvAssign = (TextView) findViewById(R.id.tv_assign);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        listAssign = (LinearLayout) findViewById(R.id.list_assign);
        swActiveM = (Switch) findViewById(R.id.sw_active);
//        btnAssign = (Button) findViewById(R.id.btn_assign);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> temps = new ArrayList<>();
        for(int i =0; i< staffs.size(); i++){
            temps.add(staffs.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, temps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for (int i = 0; i < assigns.size(); i++) {

            View childView = layoutInflater.inflate(R.layout.detail_assign_item, null);
            TextView tvName = (TextView) childView.findViewById(R.id.tv_name);
            TextView tvTime = (TextView) childView.findViewById(R.id.tv_date_assign);
            Switch swActive = (Switch) childView.findViewById(R.id.sw_active);
            tvTime.setText(MyDate.getStringYearMonthDayHMSZ(assigns.get(i).getDate()));
            tvName.setText(assigns.get(i).getStaff().getName());
            switch (assigns.get(i).getProcess()) {
                case 0: {
                    tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.print_black_15,0,0,0);
                    break;
                }
                case 1: {
                    tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.photo_black_15,0,0,0);
                    break;
                }
                case 2: {
                    tvName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.book_black_15,0,0,0);
                    break;
                }
            }
            if(assigns.get(i).getProcess() != process) swActive.setVisibility(View.INVISIBLE);
            if(assigns.get(i).getActive() == 1) swActive.setChecked(true);
            SwitchChangedActive switchChangedActive = new SwitchChangedActive(assigns.get(i));
            swActive.setOnCheckedChangeListener(switchChangedActive);
            listAssign.addView(childView);
        }
        spinner.setOnItemSelectedListener(this);
        tvAssign.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:{
                dismiss();
                break;
            }
            case R.id.tv_assign: {
                Log.d("assign-staff-for-task","asdasd1");
                JSONObject json = new JSONObject();
                try {
                    json.put("taskid",taskid);
                    json.put("staffid",staffid);
                    json.put("type",process);
                    int active = 0;
                    if(swActiveM.isChecked()) active=1;
                    json.put("active",active);
                } catch (JSONException e) {
                    Log.d("assign-staff-for-task",String.valueOf(e.getMessage()));
                }
                SocketSingleton.getInstance().getSocket().emit("assign-staff-for-task", json);
                dismiss();
                Log.d("assign-staff-for-task","asdasd2");
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
