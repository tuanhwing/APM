package graduating.project.com.apm.exclass;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Tuan on 23/12/2017.
 */

public class TimerAsync extends AsyncTask<Long, String, Void> {
    TextView textView;
    public TimerAsync (){

    }


    public TimerAsync(TextView textView){
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Long... params) {
        while(true){
            if(params[0] == 0) break;
            Log.d("error_timer_task", "inloop_" +String.valueOf(params[0]));
            try {
                Thread.sleep(1000);
                params[0] -= 1;
                publishProgress(MyDate.getDayHMSFromTime(params[0]));
            } catch (InterruptedException e) {
                Log.d("error_timer_task", "catch_loop_" + String.valueOf(e.getMessage()));
                return null;
            }
        }
        Log.d("error_timer_task", "inloop_break");
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        textView.setText(String.valueOf(values[0]));
        Log.d("error_timer_task", "inloop_updateprogress_" +String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        cancel(true);
    }
}
