package graduating.project.com.apm.callback;

import java.util.List;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 30/11/2017.
 */

public interface OnJsonToTasksCompleted {
    void onJsonToTasksCompleted(List<Task> tasks);
    void onJsonToTasksFailed(String error);
}
