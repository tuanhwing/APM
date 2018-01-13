package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.object.Staff;

/**
 * Created by Tuan on 30/12/2017.
 */

public class PopupListStaff extends Dialog {

    private Activity activity;
    private List<Staff> staffs;

    private LinearLayout listStaffs;

    public PopupListStaff(@NonNull Activity activity, List<Staff> staffs) {
        super(activity);

        this.activity = activity;
        this.staffs = staffs;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_staff);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        listStaffs = (LinearLayout) findViewById(R.id.list_staff);
        for (int i = 0; i < staffs.size(); i++) {

            View childView = layoutInflater.inflate(R.layout.item_staff, null);
            TextView tvName = (TextView) childView.findViewById(R.id.tv_name);
            tvName.setText(staffs.get(i).getName());
            listStaffs.addView(childView);
        }

    }
}
