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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import graduating.project.com.apm.dialog.PopupAssignedStaff;
import graduating.project.com.apm.exclass.InforTaskView;
import graduating.project.com.apm.model.DetailHelper;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.presenter.DetailPresenter;
import graduating.project.com.apm.socket.SocketEventDetail;
import graduating.project.com.apm.socket.SocketSingleton;
import graduating.project.com.apm.view.DetailView;


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
    public static final String ASSIGN_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String TASK_ID_TRANSITION_NAME = "tv_taskid";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

//    private TextView tvName, tvCount, tvCoverColor, tvCoverPaper, tvBookBindingType, tvPaperInfo, tvFile,tvOtherRequire;
    private TextView tvTaskId, tvTimeRequire, tvTimeCreate;
    private EditText edIssue;
    private ImageView imgSend;
    private ImageView imgAssign;
    private TextView address5;
    private View address2;
    private ImageView imageView;
    private RatingBar ratingBar;
    private RadioButton rdNew, rdProgress, rdCompleted;
    @BindView(R.id.cb_new)
    CheckBox cbNew;
    @BindView(R.id.cb_progress)
    CheckBox cbProgress;
    @BindView(R.id.cb_completed)
    CheckBox cbCompleted;
    @BindView(R.id.detail_info_task)
    LinearLayout listInfoTask;
    @BindView(R.id.layout_detailtask)
    RelativeLayout rlInfoTask;
    //[START]Button show detail task
    @BindView(R.id.img_show_detail)
    ImageView imgShowDetail;
    //[END]Button show detail task
    @BindView(R.id.tv_assign)
    TextView tvAssign;


    private Task task;
    SocketEventDetail socketEventDetail;
    private InforTaskView inforTaskView;

    private LinearLayout listContainer;
    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
        SocketSingleton.getInstance().getSocket().on("server-send-assign-result",socketEventDetail.getOnResultAssign());
        SocketSingleton.getInstance().getSocket().on("error-update-status-task",socketEventDetail.getOnErrorUpdateStatusTask());

        addControlls();
        addEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        fillContent();
        dealListView();
    }

    private void fillContent() {
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        inforTaskView = new InforTaskView(this);
        tvTaskId.setText(String.valueOf(task.getId()));

        address5.setText("AMNT. " + String.valueOf(task.getCount()));
        tvTimeRequire.setText(task.getTime_require());
        tvTimeCreate.setText(task.getTime_created());
        imgShowDetail.bringToFront();

        if(task.getStatus() == 0) cbNew.setChecked(true);
        if(task.getStatus() == 1) cbProgress.setChecked(true);
        if(task.getStatus() == 2) cbCompleted.setChecked(true);

        //Temp text view Assign
        for(Assign assign: task.getAssign()){
            tvAssign.append(assign.getStaff().getName() + "\n");
        }

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTaskId, TASK_ID_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeRequire, TIME_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeCreate, TIME_CREATE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvAssign, ASSIGN_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketSingleton.getInstance().getSocket().off("server-send-issue-to-all",socketEventDetail.getOnNewIssue());
        SocketSingleton.getInstance().getSocket().off("server-send-update-status-task",socketEventDetail.getOnUpdateStatus());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-to-all",socketEventDetail.getOnAssignTask());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-result",socketEventDetail.getOnResultAssign());
        SocketSingleton.getInstance().getSocket().off("error-update-status-task",socketEventDetail.getOnErrorUpdateStatusTask());
    }

    private void addEvents() {
        imgAssign.setOnClickListener(this);
        ratingBar.setOnClickListener(this);
        imgShowDetail.setOnClickListener(this);
        cbNew.setOnClickListener(this);
        cbProgress.setOnClickListener(this);
        cbCompleted.setOnClickListener(this);
        imgSend.setOnClickListener(this);
    }

    private void addControlls() {
        imageView = (ImageView) findViewById(R.id.image);
        tvTaskId = (TextView) findViewById(R.id.tv_taskid);
        tvTimeRequire = (TextView) findViewById(R.id.tv_time_require);
        address2 = findViewById(R.id.address2);
        tvTimeCreate = (TextView) findViewById(R.id.tv_time_create);
        address5 = (TextView) findViewById(R.id.address5);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);
        edIssue = (EditText) findViewById(R.id.ed_issue);
        imgSend = (ImageView) findViewById(R.id.img_send);

        imgAssign = (ImageView) findViewById(R.id.img_assign);
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


        //Temp detail info task view
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getName(),"name"));
        listInfoTask.addView(inforTaskView.getViewNameTask(String.valueOf(task.getCount()),"count"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getCover_color(),"cover color"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getCover_paper(),"cover paper"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getBookbinding_type(),"bookbinding type"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getPaper_info(),"paper info"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getOther_require(),"other require"));
        listInfoTask.addView(inforTaskView.getViewNameTask(task.getFile(), "file"));
        //Temp detail info task view
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_assign: {
//                SocketSingleton.getInstance().getSocket().emit("get-list-staff");
                PopupAssignedStaff cdd = new PopupAssignedStaff(DetailActivity.this, MainActivity.staffs, task.getAssign(),task.getId());
                cdd.show();
                break;
            }
            case R.id.rating: {

                break;
            }

            case R.id.img_send: {
                String content = String.valueOf(edIssue.getText());
                if(!content.isEmpty() || content != null)
                    try {
                        detailPresenter.sendNewIssueToServer(content,task.getId());
                    } catch (JSONException e) {
                        Log.d("send_issue_aaaa",String.valueOf(e.getMessage()));
                    }
                break;
            }

            case R.id.cb_new: {
                if(task.getStatus() == 0 && cbNew.isChecked()) break;
                updateStatusTaskToServer(0);
                break;
            }
            case R.id.cb_progress: {
                if(task.getStatus() == 1 && cbProgress.isChecked()) break;
                updateStatusTaskToServer(1);
                break;
            }

            case R.id.cb_completed: {
                if(task.getStatus() == 2 && cbCompleted.isChecked()) break;
                updateStatusTaskToServer(2);
                break;
            }

            case R.id.img_show_detail:{
                if(rlInfoTask.getVisibility() == View.VISIBLE){
                    Animation out = AnimationUtils.makeOutAnimation(this, true);
                    rlInfoTask.startAnimation(out);
                    rlInfoTask.setVisibility(View.INVISIBLE);
                    imgShowDetail.animate().rotation(0).start();
                } else {
                    Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                    rlInfoTask.startAnimation(in);
                    rlInfoTask.setVisibility(View.VISIBLE);
                    imgShowDetail.animate().rotation(180).start();
                }

                break;
            }
        }
    }

    public void updateStatusTaskToServer(int status){
        JSONObject json = new JSONObject();
        try {
            json.put("taskid",task.getId());
            json.put("status",status);
        } catch (JSONException e) {
            Log.d("update-status-task",String.valueOf(e.getMessage()));
        }
        SocketSingleton.getInstance().getSocket().emit("update-status-task", json);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton rb = (RadioButton) group.findViewById(checkedId);
        int status = -1;
        switch (checkedId){
            case R.id.cb_new: {
                if(task.getStatus() != 0) status = 0;
                break;
            }
            case R.id.cb_progress: {
                if(task.getStatus() != 1) status = 1;
                break;
            }
            case R.id.cb_completed: {
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
        switch (status){
            case 0: {
//                radioGroup.check(R.id.rd_1);
//                rdNew.setChecked(true);
//                rdProgress.setChecked(false);
//                rdCompleted.setChecked(false);

                cbNew.setChecked(true);
                cbProgress.setChecked(false);
                cbCompleted.setChecked(false);

                break;
            }
            case 1:{
//                radioGroup.check(R.id.rd_2);
//                rdNew.setChecked(false);
//                rdProgress.setChecked(true);
//                rdCompleted.setChecked(false);

                cbNew.setChecked(false);
                cbProgress.setChecked(true);
                cbCompleted.setChecked(false);
                break;
            }
            case 2:{
//                radioGroup.check(R.id.rd_3);
//                rdNew.setChecked(false);
//                rdProgress.setChecked(false);
//                rdCompleted.setChecked(true);

                cbNew.setChecked(false);
                cbProgress.setChecked(false);
                cbCompleted.setChecked(true);
                break;
            }
        }
        Toast.makeText(DetailActivity.this,"update status task success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateAssignTask(Assign assign) {
        task.getAssign().add(assign);
    }


}
