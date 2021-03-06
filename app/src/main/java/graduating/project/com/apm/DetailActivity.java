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
import android.widget.Switch;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import graduating.project.com.apm.dialog.PopupAssignedStaff;
import graduating.project.com.apm.dialog.PopupUpdateProcess;
import graduating.project.com.apm.exclass.InforTaskView;
import graduating.project.com.apm.exclass.MyDate;
import graduating.project.com.apm.exclass.TimerAsync;
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

    private TimerAsync timerAsync;//TImer

//    private TextView tvName, tvCount, tvCoverColor, tvCoverPaper, tvBookBindingType, tvPaperInfo, tvFile,tvOtherRequire;
    private TextView tvTaskId, tvTimeRequire, tvTimeCreate;
    private EditText edIssue;
    private ImageView imgSend;
    private ImageView imgAssign;
    private TextView address5;
    private ImageView address2;
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
    @BindView(R.id.img_update)
    ImageView imgUpdateType;
    //[START]Button show detail task
    @BindView(R.id.img_show_detail)
    ImageView imgShowDetail;
    //[END]Button show detail task
//    @BindView(R.id.tv_assign)
//    TextView tvAssign;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_assign)
    LinearLayout llAssing;
    @BindView(R.id.sw_active)
    Switch swActive;


    private Task task;
    SocketEventDetail socketEventDetail;
    private InforTaskView inforTaskView;

    private LinearLayout listContainer;
    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.user};

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
        SocketSingleton.getInstance().getSocket().on("server-send-update-type-task",socketEventDetail.getOnUpadteType());
        SocketSingleton.getInstance().getSocket().on("response-edit-task",socketEventDetail.getOnUpdateTask());
        SocketSingleton.getInstance().getSocket().on("server-send-update-active-staff",socketEventDetail.getOnActiveStaff());
        SocketSingleton.getInstance().getSocket().on("error-update-active-staff",socketEventDetail.getOnErrorActiveStaff());
        SocketSingleton.getInstance().getSocket().on("server-send-assign-to-all",socketEventDetail.getOnAssignTask());
        SocketSingleton.getInstance().getSocket().on("server-send-assign-result",socketEventDetail.getOnResultAssign());
        SocketSingleton.getInstance().getSocket().on("error-update-status-task",socketEventDetail.getOnErrorUpdateStatusTask());
        SocketSingleton.getInstance().getSocket().on("error-update-type-task",socketEventDetail.getOnErrorUpdateTypeTask());

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

        //[START] TIMER
        try{
            Log.d("error_timer_task","create1");
//            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            timerAsync.execute(Integer.parseInt(task.getTime_require()));
            Date deadline = format.parse(task.getDeadline());
            if(deadline.getTime() > System.currentTimeMillis()){
                timerAsync = new TimerAsync(tvTimeRequire);
                timerAsync.execute((deadline.getTime() - System.currentTimeMillis())/1000);
                Log.d("error_timer_task","compare 1_ " + String.valueOf(deadline.getTime() - System.currentTimeMillis()));
            } else {
                tvTimeRequire.setText(MyDate.getStringYearMonthDayHMS(task.getDeadline()));
                Log.d("error_timer_task","compare 2");
            }
            timerAsync.execute();
            Log.d("error_timer_task","create2");
        } catch (Exception e){
            Log.d("error_timer_task",String.valueOf(e.getMessage()));
        }
        //[END] TIMER

        Log.e("error_edit_task", "Detail_" + String.valueOf(task.getFile()));
    }

    private void setIconProcess() {
        switch (task.getType()){
            case 0: {
                address2.setImageResource(R.drawable.print);
                break;
            }
            case 1: {
                address2.setImageResource(R.drawable.photo);
                break;
            }
            case 2: {
                address2.setImageResource(R.drawable.book);
                break;
            }
        }
    }

    private void fillContent() {
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        inforTaskView = new InforTaskView(this);
        tvTaskId.setText(String.valueOf(task.getId()));

        address5.setText("AMNT. " + String.valueOf(task.getCount()));
        tvTimeRequire.setText(task.getTime_require());
        tvTimeCreate.setText(MyDate.getStringYearMonthDayHMS(task.getTime_created()));
        imgShowDetail.bringToFront();
        setIconProcess();

        if(task.getStatus() == 0) cbNew.setChecked(true);
        if(task.getStatus() == 1) cbProgress.setChecked(true);
        if(task.getStatus() == 2) cbCompleted.setChecked(true);

//        Temp text view Assign
//        Log.d("error_assign","Detail " + String.valueOf(task.getAssign().size()));
//        for(Assign assign: task.getAssign()){
//            tvAssign.append(assign.getStaff().getName() + "\n");
//
//        }

        tvType.setText("Process: " + task.getType());

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTaskId, TASK_ID_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeRequire, TIME_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeCreate, TIME_CREATE_TRANSITION_NAME);
        ViewCompat.setTransitionName(llAssing, ASSIGN_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketSingleton.getInstance().getSocket().off("server-send-issue-to-all",socketEventDetail.getOnNewIssue());
        SocketSingleton.getInstance().getSocket().off("server-send-update-status-task",socketEventDetail.getOnUpdateStatus());
        SocketSingleton.getInstance().getSocket().off("server-send-update-type-task",socketEventDetail.getOnUpadteType());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-to-all",socketEventDetail.getOnAssignTask());
        SocketSingleton.getInstance().getSocket().off("server-send-update-active-staff",socketEventDetail.getOnActiveStaff());
        SocketSingleton.getInstance().getSocket().off("response-edit-task",socketEventDetail.getOnUpdateTask());
        SocketSingleton.getInstance().getSocket().off("error-update-active-staff",socketEventDetail.getOnErrorActiveStaff());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-result",socketEventDetail.getOnResultAssign());
        SocketSingleton.getInstance().getSocket().off("error-update-status-task",socketEventDetail.getOnErrorUpdateStatusTask());
        SocketSingleton.getInstance().getSocket().off("error-update-type-task",socketEventDetail.getOnErrorUpdateTypeTask());
        if(timerAsync != null) {
            timerAsync.cancel(true);
            Log.d("error_timer_task","destroy");
        }
    }

    private void addEvents() {
        imgAssign.setOnClickListener(this);
        ratingBar.setOnClickListener(this);
        imgShowDetail.setOnClickListener(this);
        cbNew.setOnClickListener(this);
        cbProgress.setOnClickListener(this);
        cbCompleted.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        imgUpdateType.setOnClickListener(this);
    }

    private void addControlls() {
        imageView = (ImageView) findViewById(R.id.image);
        tvTaskId = (TextView) findViewById(R.id.tv_taskid);
        tvTimeRequire = (TextView) findViewById(R.id.tv_time_require);
        address2 = (ImageView) findViewById(R.id.address2);
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
        //Detail issue
        for (int i = 0; i < issues.size(); i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            TextView tvCMTName = (TextView) childView.findViewById(R.id.tv_cmt_name);
            TextView tvCMTDate = (TextView) childView.findViewById(R.id.tv_cmt_date);
            TextView tvCMTContent = (TextView) childView.findViewById(R.id.tv_cmt_content);

            tvCMTContent.setText(issues.get(i).getContent());
            tvCMTName.setText("Anonymous");
            tvCMTDate.setText(MyDate.getStringYearMonthDayHMSZ(issues.get(i).getDate()));
            if (i < headStrs.length) {
                headView.setImageResource(imageIds[0]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }
        }

        //Detail Assign
        List<Assign> assigns = task.getAssign();
        for(int i = 0; i<assigns.size(); i++){
            View childView = layoutInflater.inflate(R.layout.assign_item, null);
            llAssing.addView(childView);
            TextView tvName = (TextView) childView.findViewById(R.id.tv_name);
            ImageView imgProcess = (ImageView) childView.findViewById(R.id.img_process);
            if(assigns.get(i).getProcess() == task.getType())
                if(assigns.get(i).getActive() == 1) swActive.setChecked(true);
            tvName.setText(assigns.get(i).getStaff().getName());
            switch (assigns.get(i).getProcess()){
                case 0: {
                    imgProcess.setImageResource(R.drawable.print_black);
                    break;
                }
                case 1: {
                    imgProcess.setImageResource(R.drawable.photo_black);
                    break;
                }
                case 2: {
                    imgProcess.setImageResource(R.drawable.book_black);
                    break;
                }
            }
        }

       this.fillDetailTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_assign: {
//                SocketSingleton.getInstance().getSocket().emit("get-list-staff");
                PopupAssignedStaff cdd = new PopupAssignedStaff(DetailActivity.this, MainActivity.staffs, task.getAssign(),task.getId(),task.getType());
                cdd.show();
                break;
            }
            case R.id.rating: {

                break;
            }

            case R.id.img_send: {
                String content = String.valueOf(edIssue.getText());
                Log.d("send_issue_aaaa", String.valueOf(edIssue.getText()));
                if(!content.isEmpty() || content != null && content.length() > 0)
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
            case R.id.img_update: {
                PopupUpdateProcess popupUpdateProcess = new PopupUpdateProcess(this,task.getId(),task.getType());
                popupUpdateProcess.show();
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
               Log.d("error_new_issue","Detail1");

        tvCMTContent.setText(issue.getContent());
        tvCMTName.setText("Anonymous");
        headView.setImageResource(imageIds[0]);
        tvCMTDate.setText(MyDate.getStringYearMonthDayHMSZ(issue.getDate()));
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

                cbNew.setChecked(true);
                cbProgress.setChecked(false);
                cbCompleted.setChecked(false);

                break;
            }
            case 1:{

                cbNew.setChecked(false);
                cbProgress.setChecked(true);
                cbCompleted.setChecked(false);
                break;
            }
            case 2:{

                cbNew.setChecked(false);
                cbProgress.setChecked(false);
                cbCompleted.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void updateAssignTask(Assign assign) {
        task.getAssign().add(assign);
//        tvAssign.append(assign.getStaff().getName() + "\n");

        if(task.getId() != assign.getTask_id()) return;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View childView = layoutInflater.inflate(R.layout.assign_item, null);
        llAssing.addView(childView);
        TextView tvName = (TextView) childView.findViewById(R.id.tv_name);
        ImageView imgProcess = (ImageView) childView.findViewById(R.id.img_process);
        if(assign.getActive() == 1 && !swActive.isChecked()) swActive.setChecked(true);
        tvName.setText(assign.getStaff().getName());
        switch (assign.getProcess()){
            case 0: {
                imgProcess.setImageResource(R.drawable.print_black);
                break;
            }
            case 1: {
                imgProcess.setImageResource(R.drawable.photo_black);
                break;
            }
            case 2: {
                imgProcess.setImageResource(R.drawable.book_black);
                break;
            }
        }
    }

    @Override
    public void fillDetailTask() {
        //Temp detail info task view
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getName(),"name"));
            listInfoTask.addView(inforTaskView.getViewNameTask(String.valueOf(task.getCount()),"count"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getCover_color(),"cover color"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getCover_paper(),"cover paper"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getBookbinding_type(),"bookbinding type"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getPaper_info(),"paper info"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getOther_require(),"other require"));
            listInfoTask.addView(inforTaskView.getViewNameTask(task.getFile(), "file"));
        listInfoTask.addView(inforTaskView.getViewNameTask(String.valueOf(task.getType()), "type"));
        //Temp detail info task view
    }

    @Override
    public void updateTask(Task task) {
        if(this.task.getId() == task.getId()){
            this.task = task;
            //Temp detail info task view
            listInfoTask.removeAllViews();
            this.fillDetailTask();
            //Temp detail info task view
        }
    }

    @Override
    public void updateTypeTask(int taskid, int type) {
        if(this.task.getId() == taskid){
            try {
                this.task.setType(type);
                tvType.setText("Process: " + type);
                setIconProcess();
                finish();
            } catch (Exception e){
                Log.e("error_parseType", String.valueOf(e.getMessage()));
            }

        }
    }

    @Override
    public void updateStatusTaskError() {
        this.updateStatus(task.getId(),task.getStatus());
    }

    @Override
    public void updateActiveStaff(int taskid, int staffid, int process, int active) {
        if(taskid != task.getId()) return;
        int temp=0;
        while(true) {
            if(temp >= task.getAssign().size()) break;
            if(task.getAssign().get(temp).getTask_id() == taskid &&
                    task.getAssign().get(temp).getStaff_id() == staffid &&
                    task.getAssign().get(temp).getProcess() == process) {
                task.getAssign().get(temp).setActive(active);
                if(active == 1 && !swActive.isChecked()) swActive.setChecked(true);
                break;
            }
            temp++;
        }
    }


}
