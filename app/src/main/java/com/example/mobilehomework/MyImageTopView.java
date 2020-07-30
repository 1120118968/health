package com.example.mobilehomework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;


import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class MyImageTopView extends ViewGroup {
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int currentImageIndex = 0;
    private boolean fling = false;
    private Handler mHandler;
    private Context context;

    public MyImageTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        this.setOnTouchListener(new MyOnTouchListener());
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        scroller = new Scroller(context);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x11){
                    scrollToImage((currentImageIndex + 1)%getChildCount());
                }
                super.handleMessage(msg);
            }
        } ;
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if ((distanceX > 0 && getScrollX()<getWidth()*(getChildCount() - 1))||(distanceX < 0 && getScrollX()>0)){
                    scrollBy((int)distanceX,0);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(velocityX)> ViewConfiguration.get(context).getScaledMinimumFlingVelocity()){
                    if (fling = true){
                        scrollToImage((currentImageIndex - 1+ getChildCount())%getChildCount());

                    }else if (velocityX < 0 && currentImageIndex <= getChildCount() - 1){
                        fling = true;
                        scrollToImage((currentImageIndex + 1)%getChildCount());
                    }

                }
                return true;
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x11);
            }
        },0,8000);
    }

    @Override
    public void computeScroll() {

        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            postInvalidate();
        }
    }

    public void scrollToImage(int i) {
        if(i != currentImageIndex && getFocusedChild() != null && getFocusedChild()
                == getChildAt(currentImageIndex)){
            getFocusedChild().clearFocus();
            final int delta= i * getWidth() - getScrollX();
            int time = Math.abs(delta)*5 ;
            scroller.startScroll(getScrollX(),0,delta,0,time);
            invalidate();
            currentImageIndex = i;
            ((ImageSwitcherActivity)context).imgViews[currentImageIndex].setImageResource
                    (R.drawable.choosed);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i =0 ;i<getChildCount();i++){
            View child = getChildAt(i);
            child.setVisibility(i);
            child.measure(r-l,b-t);
            child.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    private class MyOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP){
                if(!fling){
                    snapToDestination();
                }
                fling = false;
            }
            return true;
        }
    }

    private void snapToDestination() {
        scrollToImage((getScrollX()+(getWidth()/2))/getWidth());
    }
    public void initImages(int[] imgIds){
        int nums = imgIds.length;
        this.removeAllViews();
        for(int i = 0;i<nums;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgIds[i]);
            this.addView(imageView);
        }
    }
}
