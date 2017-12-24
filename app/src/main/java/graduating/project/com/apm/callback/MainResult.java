package graduating.project.com.apm.callback;

import java.util.ArrayList;

import graduating.project.com.apm.object.Task;

/**
 * Created by Tuan on 19/11/2017.
 */

public interface MainResult {

    void onGetTaskSucess(ArrayList<Task> tasks);

     void onGetTaskError(String error);
}
