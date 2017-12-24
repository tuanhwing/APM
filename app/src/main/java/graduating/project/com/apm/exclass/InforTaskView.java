package graduating.project.com.apm.exclass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import graduating.project.com.apm.R;

/**
 * Created by Tuan on 23/12/2017.
 */

public class InforTaskView {
    private Activity activity;
    private LayoutInflater layoutInflater;

    public InforTaskView(){

    }

    public InforTaskView(Activity activity){
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
    }

    public View getViewNameTask(String name, String note){
        View childView = layoutInflater.inflate(R.layout.detail_task_item, null);
        TextView tvDescription = (TextView) childView.findViewById(R.id.tv_description);
        tvDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_doc, 0, 0, 0);
        tvDescription.setError(note, activity.getResources().getDrawable(R.drawable.ic_document));
        tvDescription.setText(name);
        return childView;
    }

}
