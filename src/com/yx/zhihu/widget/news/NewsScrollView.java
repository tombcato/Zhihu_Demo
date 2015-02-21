package com.yx.zhihu.widget.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.Toast;

public class NewsScrollView extends ScrollView {

	private ScrollViewListener scrollViewListener = null;
	private GestureDetector gestureDetector;
	public NewsScrollView(Context context) {
		super(context);
		init();
	}


	public NewsScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NewsScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		gestureDetector = new GestureDetector(getContext(), new MyGd());
	}
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
	@Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }  
	
	public interface ScrollViewListener{
		public void onScrollChanged(NewsScrollView newsScrollView, int x, int y,int oldx, int oldy);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev) ;
		return  super.onInterceptTouchEvent(ev);
	}
	 
	public class MyGd extends SimpleOnGestureListener{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) {
//			Toast.makeText(getContext(), "scroller", 0).show();
			if(Math.abs(distanceY) > Math.abs(distanceX)){
				return true;
			}
			return false;
		}
	}
	
}
