package graduating.project.com.apm.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import graduating.project.com.apm.R;
import graduating.project.com.apm.exclass.VectorDrawableUtils;
import graduating.project.com.apm.object.TimeLineModel;
import graduating.project.com.apm.utils.DateTimeUtils;

/**
 * Created by Tuan on 31/12/2017.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    private List<TimeLineModel> mFeedList;
    private Context mContext;

    private LayoutInflater mLayoutInflater;

    public TimelineAdapter(List<TimeLineModel> feedList) {
        mFeedList = feedList;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        TimeLineModel timeLineModel = mFeedList.get(position);
        holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        }
        else
            holder.mDate.setVisibility(View.GONE);

        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }
}
