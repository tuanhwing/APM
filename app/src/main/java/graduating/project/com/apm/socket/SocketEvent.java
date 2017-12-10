package graduating.project.com.apm.socket;

import android.app.Activity;

import com.github.nkzawa.emitter.Emitter;

import graduating.project.com.apm.presenter.MainPresenter;

/**
 * Created by Tuan on 29/11/2017.
 */

public class SocketEvent {

    private MainPresenter presenter;
    private Activity activity;
    private Emitter.Listener onListTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    presenter.newAddedTask(args);
                }
            });
        }
    };

    private Emitter.Listener onNewTask = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    presenter.newAddedTask(args);
                }
            });
        }
    };

    public SocketEvent(){
    }

    public SocketEvent(Activity activity, MainPresenter presenter){
        this.activity = activity;
        this.presenter = presenter;
    }

    public Emitter.Listener getOnListTask(){
        return onListTask;
    }

    public Emitter.Listener getNewTask(){
        return onNewTask;
    }
}
