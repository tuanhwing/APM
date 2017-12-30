package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import graduating.project.com.apm.CommonFragment;
import graduating.project.com.apm.R;
import graduating.project.com.apm.exclass.MyDate;
import graduating.project.com.apm.presenter.MainPresenter;

/**
 * Created by Tuan on 30/12/2017.
 */

public class PopupListTask extends Dialog implements View.OnClickListener {

    private Activity activity;

    private List<CommonFragment> fragments;
    private LinearLayout listTasks;
    private ImageView imgClose;
    private MainPresenter mainPresenter;

    public PopupListTask(@NonNull Activity activity, List<CommonFragment> fragments, MainPresenter mainPresenter) {
        super(activity);

        this.activity = activity;
        this.fragments = fragments;
        this.mainPresenter = mainPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_tasks);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imgClose = (ImageView) findViewById(R.id.img_close);
        imgClose.setOnClickListener(this);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        listTasks = (LinearLayout) findViewById(R.id.list_task);

        for (int i = 0; i < fragments.size(); i++) {
            View childView = layoutInflater.inflate(R.layout.layout_item_task, null);
            TextView tvId = (TextView) childView.findViewById(R.id.tv_id);
            TextView tvName = (TextView) childView.findViewById(R.id.tv_name);
            TextView tvTimeCreated = (TextView) childView.findViewById(R.id.tv_time_created);
            TextView tvDeadline = (TextView) childView.findViewById(R.id.tv_deadline);
            TextView tvAmount = (TextView) childView.findViewById(R.id.tv_amount);
            TextView tvCoverColor = (TextView) childView.findViewById(R.id.tv_cover_color);
            TextView tvCoverPaper = (TextView) childView.findViewById(R.id.tv_cover_paper);
            TextView tvBookbindingType = (TextView) childView.findViewById(R.id.tv_bookbinding_type);
            TextView tvPaperInfo = (TextView) childView.findViewById(R.id.tv_paper_info);
            TextView tvOtherRequire = (TextView) childView.findViewById(R.id.tv_other_require);
            TextView tvFile = (TextView) childView.findViewById(R.id.tv_file);
            Button btnDetail = (Button) childView.findViewById(R.id.btn_detail);

            //fill content
            tvId.setText("ID: "+ String.valueOf(fragments.get(i).getTask().getId()));
            tvName.setText("Name: "+ fragments.get(i).getTask().getName());
            tvDeadline.setText("Deadline: " + MyDate.getStringYearMonthDayHMS(fragments.get(i).getTask().getDeadline()));
            tvTimeCreated.setText("TimeCreated: " + MyDate.getStringYearMonthDayHMSZ(MyDate.getStringYearMonthDayHMSZ(fragments.get(i).getTask().getTime_created())));
            tvAmount.setText("Amout: " + String.valueOf(fragments.get(i).getTask().getCount()));
            tvCoverColor.setText("CoverColor: " + fragments.get(i).getTask().getCover_color());
            tvCoverPaper.setText("CoverPaper: " + fragments.get(i).getTask().getCover_paper());
            tvBookbindingType.setText("BookbindingType: " + fragments.get(i).getTask().getBookbinding_type());
            tvPaperInfo.setText("PaperInfo: " + fragments.get(i).getTask().getPaper_info());
            tvOtherRequire.setText("OtherRequire: " + fragments.get(i).getTask().getOther_require());
            tvFile.setText("File: " + fragments.get(i).getTask().getFile());
            final int finalI = i;
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainPresenter.setCurrentItemViewpager(finalI);
                    dismiss();
                }
            });
            listTasks.addView(childView);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close: {
                dismiss();
                break;
            }
        }
    }
}
