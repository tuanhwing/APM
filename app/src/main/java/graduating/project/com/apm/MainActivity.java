package graduating.project.com.apm;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
import graduating.project.com.apm.exclass.CustPagerTransformer;
import graduating.project.com.apm.model.MainHelper;
import graduating.project.com.apm.object.Task;
import graduating.project.com.apm.presenter.MainPresenter;
import graduating.project.com.apm.socket.SocketEvent;
import graduating.project.com.apm.socket.SocketSingleton;
import graduating.project.com.apm.view.MainView;


/**
 * Created by xmuSistone on 2016/9/18.
 */
public class MainActivity extends FragmentActivity implements MainView{

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
    private MainPagerAdapter mainPagerAdapter;

    //Content Viewpager
    private List<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用
//    private final String[] imageArray = {"assets://image1.jpg", "assets://image2.jpg", "assets://image3.jpg", "assets://image4.jpg", "assets://image5.jpg"};

    //Temp
    SocketEvent socketEvent;

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

//        dealStatusBar(); // Fix height of statusbar
        this.dealStatusBar();// Fix height of statusbar

        // 2. Init ImageLoader
        initImageLoader();

        // 3. fill content for viewpager
        mainPresenter.getListTaskFromServer();


        Log.d("json_async_aaa","activity1");
        socketEvent = new SocketEvent(MainActivity.this,mainPresenter);
        SocketSingleton.getInstance().getSocket().on("send-list-tasks-to-client",socketEvent.getOnListTask());
        SocketSingleton.getInstance().getSocket().on("server-send-new-task-to-all",socketEvent.getNewTask());
        SocketSingleton.getInstance().getSocket().connect();
        SocketSingleton.getInstance().getSocket().emit("get-list-task");
        Log.d("json_async_aaa","activity2");
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
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
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
        SocketSingleton.getInstance().getSocket().off("server-send-new-task-to-all",socketEvent.getNewTask());
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
    public void fillTasksIntoViewPager(List<Task> tasks) {
        for(Task temp : tasks){
            fragments.add(new CommonFragment(temp));
        }

        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        mainPagerAdapter = new MainPagerAdapter(super.getSupportFragmentManager(),fragments);
        viewPager.setAdapter(mainPagerAdapter);


        // 3. viewPager滑动时，调整指示器
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

//        this.updateIndicatorTv();
    }

    @Override
    public void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem = viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

    @Override
    public void showErrorLoadTask(String error) {
        Toast.makeText(MainActivity.this, error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addNewTaskIntoAdapter(Task task) {
        fragments.add(new CommonFragment(task));
        mainPagerAdapter.notifyDataSetChanged();
        this.updateIndicatorTv();
    }

}
