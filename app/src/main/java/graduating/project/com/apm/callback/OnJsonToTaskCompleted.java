package graduating.project.com.apm.callback;

import java.util.List;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 30/11/2017.
 */

public interface OnJsonToTaskCompleted {
    void onJsonToTaskCompleted(List<Task> tasks);
    void onJsonToTaskFailed(String error);
}
