package graduating.project.com.apm.exclass;

import android.os.AsyncTask;
import android.util.Log;

import graduating.project.com.apm.CommonFragment;

/**
 * Created by Tuan on 23/12/2017.
 */

public class TimerAsync extends AsyncTask<Integer, Integer, Void> {
    CommonFragment commonFragment;
    public TimerAsync (){

    }


    public TimerAsync(CommonFragment commonFragment){
        this.commonFragment = commonFragment;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        while(true){
            if(params[0] == 0) break;

            try {
                Thread.sleep(1000);
                params[0] -= 1;
                publishProgress(params);
            } catch (InterruptedException e) {
                Log.d("timer_async", String.valueOf(e.getMessage()));
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        commonFragment.updateTimerequire(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        cancel(true);
    }
}
