package com.yx.zhihu.view.customviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

 
     /**
      * 禁止viewpager滑动
      * @author Amor
      *
      */

public class CustomViewpager extends LazyViewPager {
	public boolean hasSetTouchMode = false;

	public CustomViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (hasSetTouchMode)
			return super.onInterceptTouchEvent(ev);
		else
			return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (hasSetTouchMode)
			return super.onTouchEvent(ev);
		else
			return false;
	}

	
}
