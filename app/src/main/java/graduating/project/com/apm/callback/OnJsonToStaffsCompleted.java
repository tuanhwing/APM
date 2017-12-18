package graduating.project.com.apm.callback;

import java.util.List;

import graduating.project.com.apm.object.Staff;

/**
 * Created by Tuan on 16/12/2017.
 */

public interface OnJsonToStaffsCompleted {
    void onJsonToStaffsCompleted(List<Staff> staffs);
    void onJsonToStaffsFailed(String error);
}
