package graduating.project.com.apm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import graduating.project.com.apm.exclass.DragLayout;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.view.CommonView;

import static graduating.project.com.apm.R.id.tv_assign;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener, CommonView {
    private ImageView imageView;
    private TextView tvTaskId, tvTimeRequire, tvTimeCreate, tvAssign;
    private TextView address5;
    private View address2;
    private RatingBar ratingBar;
    private ImageView imgStatus;
    private View head1, head2, head3, head4;
    private String imageUrl;

    private Task task;

    public CommonFragment(Task task){
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        tvTaskId = (TextView) dragLayout.findViewById(R.id.tv_taskid);
        tvTimeRequire = (TextView) dragLayout.findViewById(R.id.tv_time_require);
        address2 = dragLayout.findViewById(R.id.address2);
        tvTimeCreate = (TextView) dragLayout.findViewById(R.id.tv_time_create);
        tvAssign = (TextView) dragLayout.findViewById(tv_assign);
        address5 = (TextView) dragLayout.findViewById(R.id.address5);
        imgStatus = (ImageView) dragLayout.findViewById(R.id.img_status);
        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);

        this.fillContentFragment();
        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);

//        //temp
//        TimerAsync timerAsync = new TimerAsync(tvTimeRequire);
//        timerAsync.execute(100);
//        new TimerAsync(this).execute(100);
//        //temp
        dragLayout.setGotoDetailListener(this);
        return rootView;
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, DetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(tvTaskId, DetailActivity.TASK_ID_TRANSITION_NAME),
                new Pair(tvTimeRequire, DetailActivity.TIME_REQUIRE_TRANSITION_NAME),
                new Pair(address2, DetailActivity.ADDRESS2_TRANSITION_NAME),
                new Pair(tvTimeCreate, DetailActivity.TIME_CREATE_TRANSITION_NAME),
                new Pair(tvAssign, DetailActivity.ASSIGN_TRANSITION_NAME),
                new Pair(address5, DetailActivity.ADDRESS5_TRANSITION_NAME),
                new Pair(ratingBar, DetailActivity.RATINGBAR_TRANSITION_NAME),
                new Pair(head1, DetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, DetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(head3, DetailActivity.HEAD3_TRANSITION_NAME),
                new Pair(head4, DetailActivity.HEAD4_TRANSITION_NAME)
        );
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("object",task);
        intent.putExtra(DetailActivity.EXTRA_IMAGE_URL, imageUrl);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void showingNewAssign(String name){
        try{
            tvAssign.append(" | " + name);
        } catch (Exception e){
            Log.e("error_common_fg",String.valueOf(e.getMessage()));
        }
    }

    @Override
    public void updateTimerequire(String timeRequire) {
//        this.task.setTime_require(timeRequire);
        try {
            tvTimeRequire.setText(timeRequire);
        }  catch (Exception e){
            Log.e("error_common_fg",String.valueOf(e.getMessage()));
        }

    }

    @Override
    public void fillContentFragment() {
        tvTaskId.setText(String.valueOf(task.getId()));
        tvTimeRequire.setText(task.getTime_require());
        tvTimeCreate.setText(task.getTime_created());
        address5.setText("AMNT. " + String.valueOf(task.getCount()));
        for(Assign assign : task.getAssign()){
            tvAssign.append(" | " + assign.getStaff().getName());
        }
        this.showingStatus();

    }

    @Override
    public void showingStatus() {
        try {
            switch (task.getStatus()){
                case 0: {
                    imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_new));
                    break;
                }
                case 1: {
                    imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_problem));
                    break;
                }
                case 2: {
                    imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_completed));
                    break;
                }
            }
        } catch (Exception e){
            Log.e("error_common_fg",String.valueOf(e.getMessage()));
        }

    }

    @Override
    public void updateStatusTask(int status) {
        try {
            this.task.setStatus(status);
            this.showingStatus();
        } catch (Exception e){
            Log.e("error_common_fg",String.valueOf(e.getMessage()));
        }
    }

}
