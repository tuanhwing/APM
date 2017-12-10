package graduating.project.com.apm.object;

import java.io.Serializable;

/**
 * Created by Tuan on 10/12/2017.
 */

public class Issue implements Serializable {
    private int id;
    private int task_id;
    private String content;
    private String date;

    public Issue(){

    }

    public Issue(int id, int task_id, String content, String date) {
        this.id = id;
        this.task_id = task_id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
