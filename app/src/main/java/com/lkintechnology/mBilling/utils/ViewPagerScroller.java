package com.lkintechnology.mBilling.utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller {
    private int mScrollDuration = 1500;
    public ViewPagerScroller(Context context) {
        super(context);
    }
    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }


}