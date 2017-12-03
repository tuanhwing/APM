package graduating.project.com.apm.callback;

import java.util.List;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 19/11/2017.
 */

public interface MainResult {

    void onGetTaskSucess(List<Task> tasks);

     void onGetTaskError(String error);
}
