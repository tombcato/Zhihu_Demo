package com.yx.zhihu.activity;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.adapter.NewsDetailAdapter;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.StoryDetailEntity;
import com.yx.zhihu.entity.StoryExtraEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.view.customviewpager.ViewPagerCompat;
import com.yx.zhihu.view.customviewpager.ViewPagerCompat.OnPageChangeListener;
import com.yx.zhihu.widget.news.NewsDetialWidget;

/**
 * 新闻详情的Activity
 * @author Amor
 *
 */
public class NewsActivity extends BaseActivity implements OnPageChangeListener, VolleyCallBack {
	//手指在屏幕滑动，Y轴最小变化值
	private static final int FLING_MIN_DISTANCE = 10;
	protected static final int STORY_DETAIL_DONE = 1;
	protected static final int STORY_EXTRA_DONE = 2;
	private ViewPagerCompat mViewPager;
	private List<String> ids;
	private int position;
	private NewsDetialWidget newsDetialWidget;
	private boolean actionBarStatus; //true 显示，false 隐藏
	private ActionBar mActionBar;
	private GestureDetector mGestureDt;
	private int mMaxVelocity;
	private VelocityTracker mVelocityTracker;
	private boolean isCollected;
	private boolean isVoted;
	private Menu mOptionsMenu;
	private TextView tv_comment;
	private TextView tv_vote;
	private View commentView;
	private View voteView;
	private MenuItem menu_comment;
	private MenuItem menu_vote;
	private MenuItem menu_collect;
	private List<NewsDetialWidget> NewsWidgets;
	private NewsDetailAdapter newsDetailAdapter;
	private MyApiController myApiController;
	private ShareActionProvider mShareActionProvider;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case STORY_DETAIL_DONE:
				StoryDetailEntity info  = (StoryDetailEntity)msg.obj;
				Logger.e("msg", "loadUrl:" + info.getShare_url());
//				processData(info);
//				NewsWidgets.get(currentPos).processData(info);
				break;
			case STORY_EXTRA_DONE:
				StoryExtraEntity extra = (StoryExtraEntity) msg.obj;
				Logger.e("msg", "extra :" + extra.toString());
				setTitleRefresh(extra.isFavorite(), 
							  extra.getVote_status() == 0?false:true, 
							  extra.getComments()+"",
							  extra.getPopularity()+"");
				break;
			default:
				break;
			}
		};
	};
	private int currentPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_news_paging);
		setOverflowShowingAlways();
		ids = getIntent().getStringArrayListExtra("ids");
		position = getIntent().getIntExtra("position", 0);
		
		initView();
		initMenu();
		initData();
	}

	private void initView() {
		mGestureDt= new GestureDetector(this, new MygdListener());
		mMaxVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
		mActionBar = getSupportActionBar();
		mActionBar.setIcon(R.drawable.ic_logo);
		mActionBar.setDisplayShowTitleEnabled(false); 
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.ab_transport));
		mViewPager = (ViewPagerCompat) findViewById(R.id.news_pagerr);
		mViewPager.DEFAULT_OFFSCREEN_PAGES = 0;
		mViewPager.setOffscreenPageLimit(0);
	}
	
	public void initMenu(){
		LayoutInflater mInflate = APP.getApp().getInflate();
		commentView = mInflate.inflate(R.layout.action_bar_comment, null);
		voteView = mInflate.inflate(R.layout.action_bar_vote, null);
	}
	
	public void setCommentView(String num){
		TextView tv_comment = (TextView) commentView.findViewById(R.id.action_item_comment_text);
		tv_comment.setText(num + "");
	}
	public void setVoteView(boolean isVote, String num){
		TextView tv_vote = (TextView) voteView.findViewById(R.id.action_item_vote_text);
		ImageView iv_vote = (ImageView) voteView.findViewById(R.id.action_item_vote_image);
		tv_vote.setText(num + "");
		if (isVote) {
			iv_vote.setImageResource(R.drawable.news_voted);
		} else {
			iv_vote.setImageResource(R.drawable.news_vote);
		}
	}
	
	private void initData() {
//		FragmentsdfAdapter fragmentAdapter = new FragmentsdfAdapter(getSupportFragmentManager(),ids);
//		mViewPager.setAdapter(fragmentAdapter);
//		mViewPager.setCurrentItem(position);
//		fragmentAdapter.notifyDataSetChanged();
		myApiController = new MyApiController(this, this);
//		NewsWidgets = new ArrayList<NewsDetialWidget>();
//		for (int i = 0; i < ids.size(); i++) {
//			NewsDetialWidget newsDetail = new NewsDetialWidget(this);
//			NewsWidgets.add(newsDetail);
//		}
//		newsDetailAdapter = new NewsDetailAdapter(this, NewsWidgets);
		SDF widgetsAdapter = new SDF(this, ids);
		mViewPager.setAdapter(widgetsAdapter);
//		mViewPager.setAdapter(newsDetailAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(position);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mOptionsMenu = menu;
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.actionbar_news, menu);
		menu_collect = menu.findItem(R.id.menu_item_news_collect);
		menu_vote = menu.findItem(R.id.menu_item_news_vote);
		menu_comment = menu.findItem(R.id.menu_item_news_comment);
		MenuItem menu_share = menu.findItem(R.id.menu_item_news_share);
		mShareActionProvider = (ShareActionProvider)menu_share.getActionProvider();
		menu_comment.setActionView(commentView);
		menu_vote.setActionView(voteView);
		
		menu_comment.getActionView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityHelper.NewsToComment(NewsActivity.this,ids.get(currentPos));
				Toast.makeText(getApplicationContext(), "comment", 0).show();
			}
		});
		menu_vote.getActionView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "vote", 0).show();
			}
		});
		
		if (isCollected) {
			menu_collect.setIcon(R.drawable.news_collected);
		} else {
			menu_collect.setIcon(R.drawable.news_collect);
		}
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_item_news_share:
			
			break;
		case R.id.menu_item_news_collect:
					
			break;
		case R.id.menu_item_news_comment:
			
			break;
		case R.id.menu_item_news_vote:
			
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
	    }
	}
	
	private void updateCreateMenu() {
		if (Build.VERSION.SDK_INT >= 11) {
			invalidateOptionsMenu();
		} else if (mOptionsMenu != null) {
			mOptionsMenu.clear();
			onCreateOptionsMenu(mOptionsMenu);
		}
	}
	
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class SDF extends PagerAdapter{
		private List<String> ids;
		private Context ct;
		private NewsDetialWidget view;

		public SDF(Context ct,List<String> ids) {
			this.ct = ct;
			this.ids = ids;
		}
		@Override
		public int getCount() {
			return ids.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPagerCompat) container).removeView((NewsDetialWidget)object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			newsDetialWidget = new NewsDetialWidget(ct);
			newsDetialWidget.setData(ids.get(position));
			((ViewPagerCompat) container).addView(newsDetialWidget, 0);
			return newsDetialWidget;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1 ;
		}
		
		@Override
		public int getItemPosition(Object object) {
	        return POSITION_NONE;    
		}
		
		
	}
	
//	public class FragmentsdfAdapter extends FragmentPagerAdapter{
//
//		private List<String> ids;
//
//		public FragmentsdfAdapter(FragmentManager fm) {
//			super(fm);
//		}
//		public FragmentsdfAdapter(FragmentManager fm,List<String> ids) {
//			super(fm);
//			this.ids = ids;
//		}
//
//		@Override
//		public Fragment getItem(int position) {
////			NewsFragment frag = NewsFragment.newInstance(ids.get(position));
////			return frag;
//			return TestFragment.newInstance(ids.get(position));
//		}
//
//		@Override
//		public int getCount() {
//			if(ids != null && ids.size() > 0){
//				return ids.size();
//			}else{
//				return 0;
//			}
//			
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
////			((ViewPager)container).removeView((View)object);
//			super.destroyItem(container, position, object);
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int index) {
////			((ViewPager) container).addView(mViewList.get(index), 0);
//			return super.instantiateItem(container, index);
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view == object;
//		}
//
//		@Override
//		public int getItemPosition(Object object) {
//			return POSITION_NONE;
//		}
//	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		return mGestureDt.onTouchEvent(event);
//	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		try{
			mGestureDt.onTouchEvent(ev);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public void onPageSelected(int position) {
		this.currentPos = position;
		String id = ids.get(position);
		
		isCollected = false;
		isVoted = false;
		updateCreateMenu();
		
//		myApiController.getStoryDetailData(ApiConstant.resqCode_StoryDetail, id);
		myApiController.getStoryExtraData(ApiConstant.resqCode_StoryEXTRA, id);
		
	}
	
	public void setActionBarShow(){
		actionBarStatus = true;
		mActionBar.show();
	}
	public void setActionBarHidden(){
		actionBarStatus = false;
		mActionBar.hide();
	}
	
	public class MygdListener extends SimpleOnGestureListener{
		final VelocityTracker verTracker = mVelocityTracker;  
		private int mPointerId;
		private float velocityX;
		private float velocityY;
		@Override
		public boolean onDown(MotionEvent e) {
			acquireVelocityTracker(e);  
			mPointerId = e.getPointerId(0);  
			return false;
		}
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			verTracker.computeCurrentVelocity(1000, mMaxVelocity);  
			velocityX = verTracker.getXVelocity(mPointerId);  
            velocityY = verTracker.getYVelocity(mPointerId); 
            releaseVelocityTracker();
			if(Math.abs(distanceY) > Math.abs(distanceX)){
	            if(velocityY > 1 && e1.getY() - e2.getY() > FLING_MIN_DISTANCE ){
	            	setActionBarHidden();
	            }else if(velocityY > 1 && e2.getY() - e1.getY() > FLING_MIN_DISTANCE ){
	            	setActionBarShow();
	            }
	            return false;
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
		
	}
	private void acquireVelocityTracker(final MotionEvent event) {  
        if(null == mVelocityTracker) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
        mVelocityTracker.addMovement(event);  
    } 
	private void releaseVelocityTracker() {  
        if(null != mVelocityTracker) {  
            mVelocityTracker.clear();  
            mVelocityTracker.recycle();  
            mVelocityTracker = null;  
        }  
    } 
	 
	public void setTitleRefresh(boolean isCollect,boolean isVote,String comment,String vote){
		if(TextUtils.isEmpty(comment)) comment = "...";
		if(TextUtils.isEmpty(vote)) vote = "...";
//		tv_comment.setText(comment+"");
//		tv_vote.setText(vote+"");
		setCommentView(comment);
		setVoteView(isVote, vote);
		isCollected  = isCollect;
		isVoted = isVote;
		Toast.makeText(this, "title refresh" + comment, 0).show();
		menu_vote.setActionView(voteView);
		menu_comment.setActionView(commentView);
		updateCreateMenu();
	}

	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		if(entity == null){
//			Toast.makeText(this, "请求数据为空", 0).show();
			return;
		}
		Message msg = null;
		switch (resqCode) {
		case ApiConstant.resqCode_StoryDetail:
			msg = Message.obtain();
			msg.what = STORY_DETAIL_DONE;
			msg.obj = (StoryDetailEntity)entity;
			mHandler.sendMessage(msg);
			break;
		case ApiConstant.resqCode_StoryEXTRA:
		    msg = Message.obtain();
			msg.what = STORY_EXTRA_DONE;
			msg.obj = (StoryExtraEntity)entity;
			mHandler.sendMessage(msg);
			break;
		}
	}
}
