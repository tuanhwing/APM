package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import graduating.project.com.apm.R;

/**
 * Created by Tuan on 06/12/2017.
 */

public class PopupAssignedStaff extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private List<String> staffs;

    private Button btnAssign;
    private Spinner spinner;

    public PopupAssignedStaff(Activity activity, List<String> staffs) {
        super(activity);
        this.activity = activity;
        this.staffs = staffs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_assgin);

        btnAssign = (Button) findViewById(R.id.btn_assign);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, staffs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        btnAssign.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_assign: {
                Toast.makeText(activity,"assign",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
