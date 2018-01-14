package graduating.project.com.apm;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import graduating.project.com.apm.dialog.PopupListStaff;
import graduating.project.com.apm.dialog.PopupListTask;
import graduating.project.com.apm.dialog.PopupTimeLine;
import graduating.project.com.apm.exclass.CustPagerTransformer;
import graduating.project.com.apm.exclass.MyDate;
import graduating.project.com.apm.model.MainHelper;
import graduating.project.com.apm.object.Assign;
import graduating.project.com.apm.object.Issue;
import graduating.project.com.apm.object.Staff;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.object.TimeLineModel;
import graduating.project.com.apm.presenter.MainPresenter;
import graduating.project.com.apm.socket.SocketEvent;
import graduating.project.com.apm.socket.SocketSingleton;
import graduating.project.com.apm.view.MainView;


/**
 * Created by xmuSistone on 2016/9/18.
 */
public class MainActivity extends FragmentActivity implements MainView, View.OnClickListener, AdapterView.OnItemSelectedListener{

    //MVP
    private MainPresenter mainPresenter;
    private MainHelper mainHelper;

    //View
    @BindView(R.id.indicator_tv)
    TextView indicatorTv;
    @BindView(R.id.position_view)
    View positionView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    //temp
    @BindView(R.id.img_btn)
    ImageView temp;
    @BindView(R.id.img_btn1)
    ImageView temp1;
    @BindView(R.id.img_btn2)
    ImageView temp2;
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.search_view)
    SearchView searchView;

    private int filterTask = -1;


    private MainPagerAdapter mainPagerAdapter;

    //Content Viewpager
    private ArrayList<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用
    private List<TimeLineModel> mDataList = new ArrayList<>();
    public static ArrayList<Task> tasks = new ArrayList<>();
//    private final String[] imageArray = {"assets://image1.jpg", "assets://image2.jpg", "assets://image3.jpg", "assets://image4.jpg", "assets://image5.jpg"};

    //Temp
    SocketEvent socketEvent;
    public static List<Staff> staffs = new ArrayList<>();

    @BindView(R.id.sp_options)
    Spinner spinner;

    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.v_search)
    View vSearch;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.search_container)
    ViewGroup searchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // 1. Custom statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        //[START] Init MVP
        if (savedInstanceState != null) {
        }
        if (mainPresenter == null) {
            if (mainHelper == null) {
                mainHelper = new MainHelper();
            }
            mainPresenter = new MainPresenter(this, mainHelper);
        }
        //[END] Init MVP

        addEvents();

//        dealStatusBar(); // Fix height of statusbar
        this.dealStatusBar();// Fix height of statusbar

        // 2. Init ImageLoader
        initImageLoader();

        // 3. fill content for viewpager
//        mainPresenter.getListTaskFromServer();

        //temp searchview
        rlSearch.setVisibility(View.GONE);
        ((EditText)  searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setTextColor(getResources().getColor(R.color.whiteInit));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// API >= 14
                    TransitionManager.beginDelayedTransition(searchContainer);
                }
                rlSearch.setVisibility(View.GONE);
                rlToolbar.setVisibility(View.VISIBLE);
                return false;
            }
        });
//        searchView.setVisibility(View.VISIBLE);
        vSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// API >= 14
                    TransitionManager.beginDelayedTransition(searchContainer);
                }
                spinner.setSelection(0);
                rlSearch.setVisibility(View.VISIBLE);
                rlToolbar.setVisibility(View.GONE);
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("search_activity",newText);
                mainPagerAdapter.getFilter().filter(newText);
                return false;
            }
        });


        //Snipper main
        String[] data = {"All", "IN", "PHOTO", "BOOKBINDING"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        socketEvent = new SocketEvent(MainActivity.this,mainPresenter);
        SocketSingleton.getInstance().getSocket().on("send-list-tasks-to-client",socketEvent.getOnListTask());
        SocketSingleton.getInstance().getSocket().on("server-send-list-staff", socketEvent.getOnListStaffs());
        SocketSingleton.getInstance().getSocket().on("server-send-new-task-to-all",socketEvent.getOnNewTask());
        SocketSingleton.getInstance().getSocket().on("server-send-update-type-task",socketEvent.getOnUpadteTypeTask());
        SocketSingleton.getInstance().getSocket().on("server-send-revert-task",socketEvent.getOnRevertTask());
        SocketSingleton.getInstance().getSocket().on("response-edit-task",socketEvent.getOnUpdateTask());
        SocketSingleton.getInstance().getSocket().on("server-send-update-active-staff",socketEvent.getOnActiveStaff());
        SocketSingleton.getInstance().getSocket().on("error-update-active-staff",socketEvent.getOnErrorActiveStaff());
        SocketSingleton.getInstance().getSocket().on("connect-ok", socketEvent.getOnConnected());
        SocketSingleton.getInstance().getSocket().on("server-send-update-status-task", socketEvent.getOnUpdateStatusTask());
        SocketSingleton.getInstance().getSocket().on("server-send-assign-to-all", socketEvent.getOnAssignTask());
        SocketSingleton.getInstance().getSocket().on("server-send-issue-to-all", socketEvent.getOnNewIssue());
        SocketSingleton.getInstance().getSocket().connect();

    }

    private void addEvents() {
        temp.setOnClickListener(this);
        temp1.setOnClickListener(this);
        temp2.setOnClickListener(this);
        vClose.setOnClickListener(this);
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // buffer size
                .discCacheFileCount(100) //Number of buffer files
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.Singleton ImageLoader class initialization
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SocketSingleton.getInstance().getSocket().disconnect();
        SocketSingleton.getInstance().getSocket().off("send-list-tasks-to-client", socketEvent.getOnListTask());
        SocketSingleton.getInstance().getSocket().off("server-send-new-task-to-all",socketEvent.getOnNewTask());
        SocketSingleton.getInstance().getSocket().off("response-edit-task",socketEvent.getOnUpdateTask());
        SocketSingleton.getInstance().getSocket().off("server-send-update-active-staff",socketEvent.getOnActiveStaff());
        SocketSingleton.getInstance().getSocket().off("server-send-revert-task",socketEvent.getOnRevertTask());
        SocketSingleton.getInstance().getSocket().off("error-update-active-staff",socketEvent.getOnErrorActiveStaff());
        SocketSingleton.getInstance().getSocket().off("server-send-update-type-task",socketEvent.getOnUpadteTypeTask());
        SocketSingleton.getInstance().getSocket().off("connect-ok", socketEvent.getOnConnected());
        SocketSingleton.getInstance().getSocket().off("server-send-update-status-task", socketEvent.getOnUpdateStatusTask());
        SocketSingleton.getInstance().getSocket().off("server-send-assign-to-all", socketEvent.getOnAssignTask());
        SocketSingleton.getInstance().getSocket().off("server-send-issue-to-all", socketEvent.getOnNewIssue());

    }

    @Override
    public void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    @Override
    public void saveListTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void fillTasksIntoViewPager(ArrayList<CommonFragment> fragments) {
        this.fragments = fragments;
        Log.d("error_AAAAAMAIN",String.valueOf(fragments.size()));

        // 1. Animation parallax for ViewPager with setPageTransformer
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

//        mainPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(),fragments);
//        viewPager.setAdapter(mainPagerAdapter);
        this.setAdapterForViewPager(fragments);


        // 3. Adjust the indicator when viewPager slides
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        spinner.setOnItemSelectedListener(this);
//        this.updateIndicatorTv();
    }

    @Override
    public void setAdapterForViewPager(ArrayList<CommonFragment> fragments) {
        mDataList.add(new TimeLineModel("Set List Tasks", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        this.fragments = fragments;
        mainPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(), fragments, mainPresenter);
        viewPager.setAdapter(mainPagerAdapter);
        this.updateIndicatorTv();
    }

    @Override
    public void updateIndicatorTv() {
        try {
            int totalNum = viewPager.getAdapter().getCount();
            int currentItem = viewPager.getCurrentItem() + 1;
            indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
        } catch (Exception e){
            Log.e("error_updateIndicator", String.valueOf(e.getMessage()));
        }

    }

    @Override
    public void showErrorLoadTask(String error) {
        Toast.makeText(MainActivity.this, error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addNewTaskIntoAdapter(Task task) {
        tasks.add(task);
        mDataList.add(new TimeLineModel("Add New Task(taskid: " + String.valueOf(task.getId()) + ")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline

        Log.d("log_task_AAAAlist_n",String.valueOf(task.getId()));
        if(fragments.size() == 0){
            if(filterTask == -1 || task.getType() == filterTask){
                ArrayList<CommonFragment> temp = new ArrayList<>();
                temp.add(new CommonFragment(task));
                this.fillTasksIntoViewPager(temp);
            }
        }else {
            if(filterTask == -1 || task.getType() == filterTask) {
                fragments.add(new CommonFragment(task));
                mainPagerAdapter.notifyDataSetChanged();
            }

        }
        this.updateIndicatorTv();
    }

    @Override
    public void updateTask(Task task) {
        int i=0;
        while (true){
            if(i >= tasks.size()) break;
            if(tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);

                int j = 0;
                while (true) {
                    if (j >= mainPagerAdapter.getFilterList().size()) break;
                    if (mainPagerAdapter.getFilterList().get(j).getTask().getId() == task.getId()) {
                        mainPagerAdapter.getFilterList().get(j).setTask(task);
                        mainPagerAdapter.getFilterList().get(j).fillContentFragment();
                        mDataList.add(new TimeLineModel("Edit Task(taskid: " + String.valueOf(task.getId()) + ")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
                        Toast.makeText(MainActivity.this, "edit task", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    j++;
                }
                break;
            }
//            if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == task.getId()){
//                mainPagerAdapter.getFilterList().get(i).setTask(task);
//                mainPagerAdapter.getFilterList().get(i).fillContentFragment();
//                Toast.makeText(MainActivity.this, "edit task", Toast.LENGTH_SHORT).show();
//                return;
//            }
            i++;
        }
        if(i >= tasks.size()) addNewTaskIntoAdapter(task);

    }

    @Override
    public void updateStatusTask(int taskid, int status) {
        String temp = "New Update Status Task(taskid: " + taskid + ",status: ";
        switch (status){
            case 0: {
                temp += "New";
                break;
            }
            case 1: {
                temp += "Progress";
                break;
            }
            case 2: {
                temp += "Completed";
                break;
            }
        }
        temp += ")";
        mDataList.add(new TimeLineModel(temp, MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        int j=0;
        while(true){
            if(tasks.get(j).getId() == taskid){
                if(j >= tasks.size()) break;
                tasks.get(j).setStatus(status);
                int i=0;
                while(true){
                    if(i >= mainPagerAdapter.getFilterList().size()) break;
                    if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == taskid){
                        if(i >= mainPagerAdapter.getFilterList().size()) break;
                        mainPagerAdapter.getFilterList().get(i).getTask().setStatus(status);
                        mainPagerAdapter.getFilterList().get(i).showingStatus();
                        break;
                    }
                    i++;
                }
                break;
            }
            j++;
        }
    }

    @Override
    public void updateTypeTask(int taskid, int type) {
        mDataList.add(new TimeLineModel("New Update Process(taskid: " + taskid + ",type: " + type + ")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        int j=0;
        while(true){
            if(j >= tasks.size()) break;
            if(tasks.get(j).getId() == taskid){
                for(int k=0; k<tasks.get(j).getAssign().size(); k++) tasks.get(j).getAssign().get(k).setActive(0);
                tasks.get(j).setType(type);
                if(type == 3) tasks.remove(j);
                int i=0;
                while(true){//Remove current fragment
                    if(i>=fragments.size()) break;
                    if(fragments.get(i).getTask().getId() == taskid){
                        switch (filterTask) {
                            case -1: {
                                if(type >= 3 && i < mainPagerAdapter.getFilterList().size()) {
                                    mainPagerAdapter.getFilterList().get(i).getTask().setType(type);
                                    mainPagerAdapter.getFilterList().remove(i);
                                    mainPagerAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                            default: {
                                if(type != filterTask && i < mainPagerAdapter.getFilterList().size()) {
                                    mainPagerAdapter.getFilterList().get(i).getTask().setType(type);
                                    mainPagerAdapter.getFilterList().remove(i);
                                    mainPagerAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                        }
                        break;
                    }
                    i++;
//                    if(i >= tasks.size()) break;
//                    if(filterTask >= 0){
//                        if(filterTask == type) {
//                            if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == taskid){
//                                mainPagerAdapter.getFilterList().add(new CommonFragment(tasks.get(i)));
//                                mainPagerAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            if(mainPagerAdapter.getFilterList().size() > 0){
//
//                            }
//                            if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == taskid){
//                                mainPagerAdapter.getFilterList().get(i).getTask().setType(type);
//                                mainPagerAdapter.getFilterList().remove(i);
//                                mainPagerAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    } else {
//                        if(type >= 3){
//                            if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == taskid){
//                                mainPagerAdapter.getFilterList().get(i).getTask().setType(type);
//                                mainPagerAdapter.getFilterList().remove(i);
//                                mainPagerAdapter.notifyDataSetChanged();
//                                break;
//                            }
//                        }
//                    }
//
//                    i++;
                }
                if(filterTask == type ){
                    fragments.add(new CommonFragment(tasks.get(j)));
                    mainPagerAdapter.notifyDataSetChanged();
                }
                break;
            }
            j++;
        }
        this.updateIndicatorTv();
    }

    @Override
    public void updateAssignTask(Assign assign) {
        mDataList.add(new TimeLineModel("New Assign(taskid: " + String.valueOf(assign.getTask_id()) + ")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        int j=0;
        while(true){
            if(j >= tasks.size()) break;
            if(tasks.get(j).getId() == assign.getTask_id()){
                tasks.get(j).getAssign().add(assign);
                Log.d("error_assign","MainActivity_1  " + String.valueOf(tasks.get(j).getAssign().size()));
                int i=0;
                while(true){
                    if(i >= mainPagerAdapter.getFilterList().size()) break;
                    if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == assign.getTask_id()){
                        mainPagerAdapter.getFilterList().get(i).showingNewAssign(assign.getStaff().getName());
                        break;
                    }
                    i++;
                }
                break;
            }
            j++;
        }

    }

    @Override
    public void addListStaffs(List<Staff> staffs) {
        mDataList.add(new TimeLineModel("Add List Staffs", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        MainActivity.staffs = staffs;
    }

    @Override
    public void addNewIssue(Issue issue) {
        mDataList.add(new TimeLineModel("New issue(taskid: " + String.valueOf(issue.getId()) +")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        int j=0;
        while(true){
            if(j >= tasks.size()) break;
            if(tasks.get(j).getId() == issue.getTask_id()){
                tasks.get(j).getIssues().add(issue);
                int i=0;
                while(true){
                    if(i >= mainPagerAdapter.getFilterList().size()) break;
                    if(mainPagerAdapter.getFilterList().get(i).getTask().getId() == issue.getTask_id()){
//                        mainPagerAdapter.getFilterList().get(i).getTask().getIssues().add(issue);
                        Log.d("error_new_issue","MainActivity");
                        break;
                    }
                    i++;
                }
                break;
            }
            j++;
        }
    }

    @Override
    public void setCurrentItemViewpager(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void updateActiveStaff(int taskid, int staffid, int process, int active) {
        mDataList.add(new TimeLineModel("Update active staff(taskid: " + String.valueOf(taskid) + "|" + "staffid: " + String.valueOf(staffid) + "|" + "active:" + String.valueOf(process) +")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        int temp=0;
        while(true) {
            if(temp >= tasks.size()) break;
            Task temp1 = tasks.get(temp);
            int temp2 =0;
            while(true){
                if(temp2 >= temp1.getAssign().size()) break;
                if(temp1.getAssign().get(temp2).getTask_id() == taskid &&
                        temp1.getAssign().get(temp2).getStaff_id() == staffid &&
                        temp1.getAssign().get(temp2).getProcess() == process) {
                    temp1.getAssign().get(temp2).setActive(active);
                    break;
                }
                temp2++;
            }
            temp++;
        }
    }

    @Override
    public void revertTask(Task task) {
        mDataList.add(new TimeLineModel("Revert Task(taskid: " + String.valueOf(task.getId()) +")", MyDate.getYMDHMSNow(System.currentTimeMillis())));//Add timeline
        this.updateTask(task);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn: {
                PopupTimeLine popupTimeLine = new PopupTimeLine(this,mDataList);
                popupTimeLine.show();

                break;
            }

            case R.id.img_btn1:{
                PopupListTask popupListTask = new PopupListTask(this, fragments,mainPresenter);
                popupListTask.show();
                break;
            }

            case R.id.img_btn2:{
                PopupListStaff popupListStaff = new PopupListStaff(this,staffs);
                popupListStaff.show();
                break;
            }

            case R.id.v_close: {
                finish();
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                this.setAdapterForViewPager(mainPresenter.getAllTasks(tasks));
                filterTask = -1;
                break;
            }
            case 1: {
                this.setAdapterForViewPager(mainPresenter.getAllTasksType(tasks,0));
                filterTask = 0;
                break;
            }
            case 2: {
                this.setAdapterForViewPager(mainPresenter.getAllTasksType(tasks,1));
                filterTask = 1;
                break;
            }
            case 3: {
                this.setAdapterForViewPager(mainPresenter.getAllTasksType(tasks,2));
                filterTask = 2;
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
