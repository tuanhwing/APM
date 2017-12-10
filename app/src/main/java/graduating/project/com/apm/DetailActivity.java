package graduating.project.com.apm;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.Slider;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.dialog.PopupAssignedStaff;
import graduating.project.com.apm.object.Task;

/**
 * Created by xmuSistone on 2016/9/19.
 */
public class DetailActivity extends FragmentActivity implements View.OnClickListener{

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String TIME_REQUIRE_TRANSITION_NAME = "tv_time_require";
    public static final String ADDRESS2_TRANSITION_NAME = "address2";
    public static final String TIME_CREATE_TRANSITION_NAME = "address3";
    public static final String OTHER_REQUIRE_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String TASK_ID_TRANSITION_NAME = "tv_taskid";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";

    private TextView tvName, tvCount, tvCoverColor, tvCoverPaper, tvSize, tvPaperColor, tvPaperType, tvBookBindingType, tvCopyCount, tvCopyColor, tvCopyPaperType, tvFile;
    private TextView tvTaskId, tvTimeRequire, tvTimeCreate, tvOtherRequire;
    private Slider address5;
    private View address2;
    private ImageView imageView;
    private RatingBar ratingBar;

    private Task task;

    private LinearLayout listContainer;
    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // To retrieve object in second Activity
        task = (Task) getIntent().getSerializableExtra("object");

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
        tvSize.setText("SIZE: " + task.getSize());
        tvPaperColor.setText("PAPER_COLOR: " + task.getPaper_color());
        tvPaperType.setText("PAPER_TYPE: " + task.getPaper_type());
        tvBookBindingType.setText("BOOKBINDING_TYPE: " + task.getBookbinding_type());
        tvCopyCount.setText("COPY_COUNT: " + task.getCopy_count());
        tvCopyColor.setText("COPY_COLOR: " + task.getCopy_color());
        tvCopyPaperType.setText("COPY_PAPER_TYPE: " + task.getCopy_paper_type());
        tvFile.setText("FILE: " + task.getFile());

        tvTimeRequire.setText(task.getTime_require());
        tvTimeCreate.setText(task.getTime_created());
        tvOtherRequire.setText(task.getOther_require());
//        address5.setText("NO. " + String.valueOf(task.getCount()));

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTaskId, TASK_ID_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeRequire, TIME_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvTimeCreate, TIME_CREATE_TRANSITION_NAME);
        ViewCompat.setTransitionName(tvOtherRequire, OTHER_REQUIRE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);

        dealListView();
    }

    private void addEvents() {
        tvOtherRequire.setOnClickListener(this);
        ratingBar.setOnClickListener(this);
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
        tvSize = (TextView) findViewById(R.id.tv_size);
        tvPaperColor = (TextView) findViewById(R.id.tv_paper_color);
        tvPaperType = (TextView) findViewById(R.id.tv_paper_type);
        tvBookBindingType = (TextView) findViewById(R.id.tv_bookbinding_type);
        tvCopyCount = (TextView) findViewById(R.id.tv_copy_count);
        tvCopyColor = (TextView) findViewById(R.id.tv_copy_color);
        tvCopyPaperType = (TextView) findViewById(R.id.tv_copy_paper_type);
        tvFile = (TextView) findViewById(R.id.tv_file);

    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            ImageView headView = (ImageView) childView.findViewById(R.id.head);
            TextView tvCMTName = (TextView) childView.findViewById(R.id.tv_cmt_name);
            TextView tvCMTDate = (TextView) childView.findViewById(R.id.tv_cmt_date);
            TextView tvCMTContent = (TextView) childView.findViewById(R.id.tv_cmt_content);

            tvCMTContent.setText("Content Comment");
            tvCMTName.setText("Name Comment");
            tvCMTDate.setText("Data Comment");
            if (i < headStrs.length) {
                headView.setImageResource(imageIds[i % imageIds.length]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_other_require: {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Assigned Staff");
//
//                ListView modeList = new ListView(this);
//                String[] stringArray = new String[] { "Bright Mode", "Normal Mode" };
//                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
//                modeList.setAdapter(modeAdapter);
//
//                builder.setView(modeList);
//                final Dialog dialog = builder.create();
//
//                dialog.show();

                List<String> temps = new ArrayList<>();
                temps.add("LOL1");
                temps.add("LOL2");
                temps.add("LOL3");
                PopupAssignedStaff cdd = new PopupAssignedStaff(DetailActivity.this,temps);
                cdd.show();
                break;
            }
            case R.id.rating: {

                break;
            }
        }
    }
}
