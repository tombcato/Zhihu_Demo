package com.yx.zhihu.widget.home;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.adapter.ViewPageAdapter;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.view.customviewpager.indicator.CirclePageIndicator;

public class HomeHeadWidget extends RelativeLayout implements OnClickListener, OnPageChangeListener {

	protected static final int FINISH = 1;
	private static final long CHANGE_TIME = 5000;
	protected static final float HORIZONTALMINDISTANCE = 20;
	protected static final float VERTICALMINVELOCITY = 0;
	private View view;
	private List<StoryEntity> topNews;
	private LayoutInflater mInflater;
	private List<View> viewLists;
	private ViewPageAdapter viewPagerAdapter;
	private ViewPager mViewPager;
	private CirclePageIndicator mIndicator;
	private RelativeLayout topHead;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FINISH:
					int currentItem = mViewPager.getCurrentItem();
					int item  = currentItem + 1;
					mHandler.removeMessages(FINISH);
					mViewPager.setCurrentItem(item,true);
//					Toast.makeText(getContext(), "msg chang" + currentItem, 0).show();
				break;
			default:
				break;
			}
		};
	};
	private boolean isOnTouch;
	private GestureDetector mGesture;

	public HomeHeadWidget(Context context) {
		super(context);
		init();
	}

	public HomeHeadWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public HomeHeadWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	

	private void init() {
		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.fragment_home_header, null);
		
		addView(view);
		initView();
		setListener();
	}

	private void initView() {
		viewLists = new ArrayList<View>();
		topHead = (RelativeLayout) view.findViewById(R.id.topstory);
		mViewPager = (ViewPager) view.findViewById(R.id.topstory_pager);
//	    mIndicator = new CirclePageIndicator(getContext());
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.topstory_pager_indicator);
//	    mIndicator.
		RelativeLayout.LayoutParams headParams = (LayoutParams) topHead.getLayoutParams();
		headParams.height = APP.getApp().getResolution().px2dp2px(460, false);
		
	}
	
	public void setData(List<StoryEntity> topNews) {
		this.topNews = topNews;
		viewLists.clear();
		
		
		for (int i = 0; i < topNews.size(); i++) {
			View view_item = drawItem(topNews, i);
			viewLists.add(view_item);
		}
		if(topNews != null && topNews.size() > 1){
			viewLists.add(0, drawItem(topNews, topNews.size() - 1));
			viewLists.add(drawItem(topNews, 0));
		}
		
		changeViewPageScroller();
		viewPagerAdapter = new ViewPageAdapter(viewLists);
		mViewPager.setAdapter(viewPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(1);
		mGesture = new GestureDetector(getContext(), mOnGesture);
		mIndicator.setViewPager(mViewPager,1);
		mIndicator.setOnPageChangeListener(this);
		mIndicator.setLoopStaus(true);
		
	}

	private View drawItem(List<StoryEntity> topNews, int i) {
		View view_item = mInflater.inflate(R.layout.fragment_home_header_topstory_pager, null);
		ImageView iv_img = (ImageView) view_item.findViewById(R.id.topstory_image);
		TextView tv_title = (TextView) view_item.findViewById(R.id.topstory_title);
		RelativeLayout rl_mask = (RelativeLayout) view_item.findViewById(R.id.topstroy_mask);
		rl_mask.setBackgroundColor(Color.parseColor("#55000000"));
		iv_img.setScaleType(ScaleType.CENTER_CROP);
//		ImageLoader.getInstance().displayImage(topNews.get(i).getImage(), iv_img);
		ImageLoader.getInstance().displayImage(topNews.get(i).getImage(), iv_img, APP.getApp().getDefalutOptions());
		tv_title.setText(topNews.get(i).getTitle());
		view_item.setTag(i);
		view_item.setOnClickListener(this);
		return view_item;
	}

	private void setListener() {
		
	}

	@Override
	public void onClick(View v) {
		
		ArrayList<String> ids = new ArrayList<String>();
		for (StoryEntity info : topNews) {
			ids.add(info.getId());
		}
		int position = (Integer) v.getTag();
		ActivityHelper.HomeToDetail(getContext(),ids,position);
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(arg0 == ViewPager.SCROLL_STATE_IDLE){
			int currentIndex = mViewPager.getCurrentItem();
			if(currentIndex == 0){
				mViewPager.setCurrentItem(viewLists.size() - 2, false);
//				mHandler.removeMessages(FINISH);
			}else if(currentIndex == viewLists.size() - 1){
				mViewPager.setCurrentItem(1, false);
//				mHandler.removeMessages(FINISH);
			}
		}
		
		switch (arg0) {
		case ViewPager.SCROLL_STATE_DRAGGING:
			isOnTouch = true; //手势滑动
			mHandler.removeMessages(FINISH);
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			isOnTouch = false;
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onPageSelected(int arg0) {
//		Toast.makeText(getContext(), "sec :" + arg0, 0).show();
		mHandler.sendEmptyMessageDelayed(FINISH, CHANGE_TIME);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGesture.onTouchEvent(event);
	}
	
	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			
			if(e1.getX() - e2.getX() > HORIZONTALMINDISTANCE && Math.abs(velocityX) > VERTICALMINVELOCITY ){//��
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1,true);
				mHandler.removeMessages(FINISH);
				
			}else if(e2.getX() - e1.getX() > HORIZONTALMINDISTANCE && Math.abs(velocityX) > VERTICALMINVELOCITY ){//��
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1,true);
				mHandler.removeMessages(FINISH);
				
			}
			return true;
		};
	};
	private float mLastMotionX;
	private float mLastMotionY;
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final float x = ev.getX();
		final float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(x - mLastMotionX) < Math.abs(y - mLastMotionY)) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	private void changeViewPageScroller() {
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			FixedSpeedScroller scroller;
			scroller = new FixedSpeedScroller(getContext(),new AccelerateDecelerateInterpolator());
			mField.set(mViewPager, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	class FixedSpeedScroller extends Scroller {
		private int mDuration = 400;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}
	};
}
