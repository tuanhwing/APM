package graduating.project.com.apm;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.Slider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import graduating.project.com.apm.dialog.PopupAssignedStaff;
import graduating.project.com.apm.model.DetailHelper;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.presenter.DetailPresenter;
import graduating.project.com.apm.socket.SocketEventDetail;
import graduating.project.com.apm.socket.SocketSingleton;
import graduating.project.com.apm.view.DetailView;

import static graduating.project.com.apm.R.id.btn_assign;

/**
 * Created by xmuSistone on 2016/9/19.
 */
public class DetailActivity extends FragmentActivity implements DetailView, View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    //MVP
    private DetailPresenter detailPresenter;
    private DetailHelper detailHelper;

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String TIME_REQUIRE_TRANSITION_NAME = "tv_time_require";
    public static final String ADDRESS2_TRANSITION_NAME = "address2";
    public static final String TIME_CREATE_TRANSITION_NAME = "address3";
//    public static final String OTHER_REQUIRE_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String TASK_ID_TRANSITION_NAME = "tv_taskid";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

    private TextView tvName, tvCount, tvCoverColor, tvCoverPaper, tvBookBindingType, tvPaperInfo, tvFile;
    private TextView tvTaskId, tvTimeRequire, tvTimeCreate, tvOtherRequire;
    private Button btnAssign;
    private Slider address5;
    private View address2;
    private ImageView imageView;
    private RatingBar ratingBar;
    private RadioGroup radioGroup;

    private Task task;
    SocketEventDetail socketEventDetail;

    private LinearLayout listContainer;
    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //[START] Init MVP
        if (savedInstanceState != null) {
        }
        if (detailPresenter == null) {
            if (detailHelper == null) {
                detailHelper = new DetailHelper();
            }
            detailPresenter = new DetailPresenter(this, detailHelper);
        }
        //[END] Init MVP

        // To retrieve object in second Activity
        task = (Task) getIntent().getSerializableExtra("object");
        socketEventDetail = new SocketEventDetail(DetailActivity.this, detailPresenter);
        SocketSingleton.getInstance().getSocket().on("server-send-issue-to-all",socketEventDetail.getOnNewIssue());
        SocketSingleton.getInstance().getSocket().on("server-send-update-status-task",socketEventDetail.getOnUpdateStatus());
        SocketSingleton.getInstance().getSocket().on("server-send-assign-to-all",socketEventDetail.getOnAssignTask());

        addControlls();
        addEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        tvTaskId.setText(String.valueOf(task.getId()));
        tvName.setText("NAME: " + task.getName());
        tvCount.setText("COUNT: " + String.valueOf(task.getCount()));
        tvCoverColor.setText("COVER_COLOR: " + task.getCover_color());
        tvCoverPaper.setText("COVER_PAPER: " + task.getCover_paper());
        tvPaperInfo.setText("PAPER INFO: " + task.getPaper_info());
        tvBookBindingType.setText("BOOKBINDING_TYPE: " + task.getBookbinding_type());
        tvFile.setText("FILE: " + task.getFile());

        tvTimeRequire.setText(task.getTime_require());
        tvTimeCreate.setText(task.getTime_created());
        tvOtherRequire.setText("OTHER_REQUIRE: " + task.getOther_require());
//        address5.setText("NO. " + String.valueOf(task.getCount()));
        if(task.getStatus() == 0) radioGroup.check(R.id.rd_1);
        if(task.getStatus() == 1) radioGroup.check(R.id.rd_2);
        if(task.getStatus() == 2) radioGroup.check(R.id.rd_3);

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTaskId, TASK_ID_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeRequire, TIME_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeCreate, TIME_CREATE_TRANSITION_NAME);
//        ViewCompat.setTransitionName(tvOtherRequire, OTHER_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);

        dealListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketSingleton.getInstance().getSocket().off("server-send-issue-to-all",socketEventDetail.getOnNewIssue());
        SocketSingleton.getInstance().getSocket().off("server-send-update-status-task",socketEventDetail.getOnUpdateStatus());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-to-all",socketEventDetail.getOnAssignTask());
    }

    private void addEvents() {
//        tvOtherRequire.setOnClickListener(this);
        btnAssign.setOnClickListener(this);
        ratingBar.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void addControlls() {
        imageView = (ImageView) findViewById(R.id.image);
        tvTaskId = (TextView) findViewById(R.id.tv_taskid);
        tvTimeRequire = (TextView) findViewById(R.id.tv_time_require);
        address2 = findViewById(R.id.address2);
        tvTimeCreate = (TextView) findViewById(R.id.tv_time_create);
        tvOtherRequire = (TextView) findViewById(R.id.tv_other_require);
        address5 = (Slider) findViewById(R.id.address5);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvCoverColor = (TextView) findViewById(R.id.tv_cover_color);
        tvCoverPaper = (TextView) findViewById(R.id.tv_cover_paper);
        tvPaperInfo = (TextView) findViewById(R.id.tv_paper_info);
        tvBookBindingType = (TextView) findViewById(R.id.tv_bookbinding_type);
        tvFile = (TextView) findViewById(R.id.tv_file);
        btnAssign = (Button) findViewById(btn_assign);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        List<Issue> issues = task.getIssues();
        for (int i = 0; i < issues.size(); i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            TextView tvCMTName = (TextView) childView.findViewById(R.id.tv_cmt_name);
            TextView tvCMTDate = (TextView) childView.findViewById(R.id.tv_cmt_date);
            TextView tvCMTContent = (TextView) childView.findViewById(R.id.tv_cmt_content);

            tvCMTContent.setText(issues.get(i).getContent());
            tvCMTName.setText("name");
            tvCMTDate.setText(issues.get(i).getDate());
            if (i < headStrs.length) {
                headView.setImageResource(imageIds[0]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_assign: {
//                SocketSingleton.getInstance().getSocket().emit("get-list-staff");
                PopupAssignedStaff cdd = new PopupAssignedStaff(DetailActivity.this, MainActivity.staffs, task.getAssign(),task.getId());
                cdd.show();
                break;
            }
            case R.id.rating: {

                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        int status = -1;
        switch (checkedId){
            case R.id.rd_1: {
                if(task.getStatus() != 0) status = 0;
                break;
            }
            case R.id.rd_2: {
                if(task.getStatus() != 1) status = 1;
                break;
            }
            case R.id.rd_3: {
                if(task.getStatus() != 2) status = 2;
                break;
            }
        }
        if(status >= 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("taskid",task.getId());
                json.put("status",status);
            } catch (JSONException e) {
                Log.d("update-status-task",String.valueOf(e.getMessage()));
            }
            SocketSingleton.getInstance().getSocket().emit("update-status-task", json);
        }
    }

    @Override
    public void addNewIssue(Issue issue) {
        if(task.getId() != issue.getTask_id()) return;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
        listContainer.addView(childView);
        ImageView headView = (ImageView) childView.findViewById(R.id.head);
        TextView tvCMTName = (TextView) childView.findViewById(R.id.tv_cmt_name);
        TextView tvCMTDate = (TextView) childView.findViewById(R.id.tv_cmt_date);
        TextView tvCMTContent = (TextView) childView.findViewById(R.id.tv_cmt_content);

        tvCMTContent.setText(issue.getContent());
        tvCMTName.setText("name");
        headView.setImageResource(imageIds[0]);
        tvCMTDate.setText(issue.getDate());
//        if (i < headStrs.length) {
//            headView.setImageResource(imageIds[0]);
//            ViewCompat.setTransitionName(headView, headStrs[i]);
//        }
    }

    @Override
    public void updateStatus(int taskid, int status) {
        if(taskid != task.getId()) return;
        task.setStatus(status);
        Toast.makeText(DetailActivity.this,"update status task success", Toast.LENGTH_LONG).show();
    }
}
