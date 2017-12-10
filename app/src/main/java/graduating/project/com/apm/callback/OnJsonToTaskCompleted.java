package graduating.project.com.apm.callback;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 10/12/2017.
 */

public interface OnJsonToTaskCompleted {

    void onJsonToTaskCompleted(Task task);
    void onJsonToTaskFailed(String error);
}
