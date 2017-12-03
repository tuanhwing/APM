package graduating.project.com.apm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import graduating.project.com.apm.exclass.DragLayout;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.view.CommonView;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener, CommonView {
    private ImageView imageView;
    private TextView address0, address1, address3, address4, address5;
    private View address2;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4;
    private String imageUrl;

    private Task task;

    public CommonFragment(Task task){
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        address0 = (TextView) dragLayout.findViewById(R.id.address0);
        address1 = (TextView) dragLayout.findViewById(R.id.address1);
        address2 = dragLayout.findViewById(R.id.address2);
        address3 = (TextView) dragLayout.findViewById(R.id.address3);
        address4 = (TextView) dragLayout.findViewById(R.id.address4);
        address5 = (TextView) dragLayout.findViewById(R.id.address5);
        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);

        this.fillContentFragment();
        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);


        dragLayout.setGotoDetailListener(this);
        return rootView;
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, DetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(address0, DetailActivity.ADDRESS0_TRANSITION_NAME),
                new Pair(address1, DetailActivity.ADDRESS1_TRANSITION_NAME),
                new Pair(address2, DetailActivity.ADDRESS2_TRANSITION_NAME),
                new Pair(address3, DetailActivity.ADDRESS3_TRANSITION_NAME),
                new Pair(address4, DetailActivity.ADDRESS4_TRANSITION_NAME),
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
    public void updateTimerequire(String timeRequire) {
        address1.setText(timeRequire);
    }

    @Override
    public void fillContentFragment() {
        address0.setText(String.valueOf(task.getId()));
        address1.setText(task.getTime_require());
        address3.setText(task.getTime_created());
        address4.setText(task.getName());
        address5.setText("NO. " + String.valueOf(task.getCount()));

    }
}
