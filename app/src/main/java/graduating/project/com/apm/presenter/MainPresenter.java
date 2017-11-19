package graduating.project.com.apm.presenter;

import graduating.project.com.apm.callback.MainResult;
import graduating.project.com.apm.model.MainHelper;
import graduating.project.com.apm.view.MainView;

/**
 * Created by Tuan on 19/11/2017.
 */

public class MainPresenter implements MainResult{
    private MainView view;
    private MainHelper model;

    public MainPresenter(MainView view, MainHelper model){
        this.view = view;
        this.model = model;
        this.model.setOnMainResult(this);
    }

}
