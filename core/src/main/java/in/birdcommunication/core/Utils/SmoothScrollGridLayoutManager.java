package in.birdcommunication.core.Utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;

/**
 * Optimized implementation of GridLayoutManager to SmoothScroll to a Top position.
 *
 * @since 5.0.0-b6 Creation in main package
 * <br>17/12/2017 Moved into UI package
 */
public class SmoothScrollGridLayoutManager extends GridLayoutManager implements IFlexibleLayoutManager {

    private RecyclerView.SmoothScroller mSmoothScroller;

    public SmoothScrollGridLayoutManager(Context context, int spanCount) {
        this(context, spanCount, VERTICAL, false);
    }

    public SmoothScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        mSmoothScroller = new TopSnappedSmoothScroller(context, this);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        mSmoothScroller.setTargetPosition(position);
        startSmoothScroll(mSmoothScroller);
    }

}