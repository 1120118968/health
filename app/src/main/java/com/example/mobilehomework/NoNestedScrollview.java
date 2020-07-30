package com.example.mobilehomework;



import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;




public class NoNestedScrollview extends NestedScrollView {

    private int downX;
    private int downY;
    private int mTouchSlop;

    public NoNestedScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoNestedScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoNestedScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    private NestedScrollViewListener scrollViewListener = null;
    public void setScrollViewListener(NestedScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scrollViewListener!=null){
            scrollViewListener.onScroll(getScrollY());
        }
    }
    interface NestedScrollViewListener {

        /*** 在滑动的时候调用*/
        void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy);
        /*** 在滑动的时候调用，scrollY为已滑动的距离*/
        void onScroll(int scrollY);

    }
}


