package graduating.project.com.apm.object;

import java.io.Serializable;

/**
 * Created by Tuan on 10/12/2017.
 */

public class Assign implements Serializable {
    private int task_id;
    private int staff_id;
    private String date;
    private Staff staff;

    public Assign(){

    }

    public Assign(int task_id, int staff_id, String date, Staff staff) {
        this.task_id = task_id;
        this.staff_id = staff_id;
        this.date = date;
        this.staff = staff;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
