package graduating.project.com.apm.exclass;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import graduating.project.com.apm.R;

/**
 * Created by Tuan on 06/12/2017.
 */

public class ItemComment extends View {
    private View view;


    public ItemComment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.detail_list_item, null);
    }
}
