package graduating.project.com.apm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.object.OrderStatus;
import graduating.project.com.apm.object.TimeLineModel;
import graduating.project.com.apm.timeline.TimelineAdapter;

/**
 * Created by Tuan on 31/12/2017.
 */

public class PopupTimeLine extends Dialog implements View.OnClickListener {

    private Activity activity;
    private ImageView imgClose;
    private TimelineAdapter mTimeLineAdapter;
    private RecyclerView mRecyclerView;
    private List<TimeLineModel> mDataList = new ArrayList<>();

    public PopupTimeLine(@NonNull Activity activity) {
        super(activity);

        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.layout_timeline);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        imgClose = (ImageView) findViewById(R.id.img_close);
        imgClose.setOnClickListener(this);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        initView();
    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimelineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    private void setDataListItems(){
        mDataList.add(new TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE));
        mDataList.add(new TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED));
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
