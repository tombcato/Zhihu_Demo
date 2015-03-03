package com.yx.zhihu.activity;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.print.PrintAttributes.Resolution;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.common.DoubleClickExitHelper;
import com.yx.zhihu.entity.DrawerEntity;
import com.yx.zhihu.entity.SplashEntity;
import com.yx.zhihu.entity.ThemeEntity;
import com.yx.zhihu.fragment.DrawerFragment;
import com.yx.zhihu.fragment.HomeFragment;
import com.yx.zhihu.fragment.SuberFragment;
import com.yx.zhihu.fragment.TestFragment;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.ResolutionUtil;

public class MainActivity extends BaseActivity implements VolleyCallBack{
	static final String DRAWER_MENU_TAG = "drawer_menu";
	static final String DRAWER_CONTENT_TAG = "drawer_content";
	static final String DRAWER_THEMESUB_TAG = "drawer_themeSub";
	private DrawerFragment mMenu;
	private HomeFragment mHome;
	private DoubleClickExitHelper mDoubleClickExitHelper;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private FragmentManager mFragmentManager;
	private MyApiController controller;
	private List<DrawerEntity> drawers;
	private DrawerEntity homeTag;
	private SuberFragment mSubNews;
	private ActionBar mActionBar;
	private TestFragment mTest;
	private String imgDesc;
	private String imgUrl;
	private ResolutionUtil resolution;
	private ImageView iv_splash;
	private TextView tv_imgDesc;
	private View loadPage;
	private RelativeLayout rl_main;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			rl_main.removeView(loadPage);
			mActionBar.show();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		initLoadPage();
		initView(savedInstanceState);
		initData();
	}


	private void init() {
		APP app = APP.getApp();
		resolution = app.getResolution();
	}


	private void initLoadPage() {
		rl_main = (RelativeLayout) findViewById(R.id.main);
		loadPage = mInflater.inflate(R.layout.loading_page,null);
		rl_main.addView(loadPage);
		iv_splash = (ImageView) loadPage.findViewById(R.id.iv_splash);
		ImageView iv_logo = (ImageView) loadPage.findViewById(R.id.iv_logo);
		tv_imgDesc = (TextView) loadPage.findViewById(R.id.tv_img_desc);
		tv_imgDesc.setTextSize(resolution.px2sp2px(22));
		
		RelativeLayout.LayoutParams logoParams = (android.widget.RelativeLayout.LayoutParams) iv_logo.getLayoutParams();
		RelativeLayout.LayoutParams tvParams = (android.widget.RelativeLayout.LayoutParams) tv_imgDesc.getLayoutParams();
		tvParams.topMargin = resolution.px2dp2px(10, false);
	}


	private void initView(Bundle savedInstanceState) {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setIcon(R.drawable.ic_logo);
		mActionBar.hide();
		
		mMenu = DrawerFragment.newInstance();
		mHome = HomeFragment.newInstance();
		mTest = TestFragment.newInstance("123");
		mSubNews = SuberFragment.newInstance(-1);
		
		mMenu.setMenuCallBack(new mMenuCallBack());
		mDoubleClickExitHelper = new DoubleClickExitHelper(this);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		FrameLayout slidingMenu = (FrameLayout) findViewById(R.id.main_slidingmenu_frame);
		LayoutParams menu_Params = slidingMenu.getLayoutParams();
		menu_Params.width = APP.getApp().getResolution().px2dp2px(460, true);
//		mDrawerLayout.setDrawerListener(new DrawerMenuListener());
		// 设置滑出菜单的阴影效果
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer,  R.string.drawer_open,  R.string.drawer_close);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		mFragmentManager = getSupportFragmentManager();
//		if (null == savedInstanceState) {
			setExploreShow();
//		}
	}

	private void initData() {
		drawers = new ArrayList<DrawerEntity>();
		controller = new MyApiController(this, this);
		getLoadingPage();
		getThemelists();
	}
	public void getLoadingPage(){
		controller.getSplashData(ApiConstant.resqCode_splash);
	}
	public void getThemelists() {
		controller.getThemeLists(ApiConstant.resqCode_Themelist);
	}
	public void addSubTheme(int id) {
		controller.getAddSubTheme(ApiConstant.resqCode_ThemeSubADD, id);
	}
	public void getSubThemeNews(int id){
		mSubNews.initSubThemeNews(id);
	}

	private void setExploreShow() {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		ft.replace(R.id.main_slidingmenu_frame,mMenu, DRAWER_MENU_TAG)
		  .replace(R.id.main_content,mHome,DRAWER_CONTENT_TAG)
		  .commit();
	}
	
	 @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		if(entity == null){
			APP.getApp().getToast().getSingletonToast("themes load fail");
		}
		switch (resqCode) {
		case ApiConstant.resqCode_splash:
			SplashEntity enty = (SplashEntity)entity;
			imgDesc = enty.getText();
			imgUrl = enty.getImg();
			processData();
			break;
		case ApiConstant.resqCode_Themelist:
			drawers.clear();
			ThemeEntity info = (ThemeEntity)entity;
			List<DrawerEntity> others = info.getOthers();
			List<DrawerEntity> subscribed = info.getSubscribed();
			for (DrawerEntity ent : subscribed) {
				ent.setSubTheme(true);
			}
			if(homeTag == null){
				homeTag = new DrawerEntity();
				homeTag.setId(0);
				homeTag.setName("首页");
				homeTag.setSubTheme(true);
			}
			drawers.add(homeTag);
			drawers.addAll(subscribed);
			drawers.addAll(others);
			mMenu.setData(drawers);
			break;

		default:
			break;
		}
	}
	private void processData() {
		tv_imgDesc.setText(imgDesc);
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
//            .showStubImage(R.drawable.splash)          // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.splash)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.splash)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .build();                                   // 创建配置过得DisplayImageOption对象  
		ImageLoader.getInstance().displayImage(imgUrl, iv_splash, options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				Animation anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.splash);
				anim.setFillAfter(true);
				anim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// 动画结束时跳转至首页  
						mHandler.sendEmptyMessage(1);
					}
				});
				
				view.startAnimation(anim);
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});

	}
	private class mMenuCallBack implements ClickCallBack{

		@Override
		public void onThemeListClick(int position) {
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			if(position == 0){
				mHome = HomeFragment.newInstance();
				ft.replace(R.id.main_content, mHome, DRAWER_CONTENT_TAG).commit();
//				mHome.initData(true);
			}else{
				toSubNews(drawers.get(position).getId());
//				mSubNews.initSubThemeNews(drawers.get(position).getId());
			}
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}

		@Override
		public void onLoginClick() {
			
		}

		@Override
		public void onFavoriteClick() {
			
		}

		@Override
		public void onSettingClick() {
			
		}

		@Override
		public void onCollectionClick() {
			
		}
		
	}
	public interface ClickCallBack{
		void onThemeListClick(int position);
		void onLoginClick();
		void onFavoriteClick();
		void onSettingClick();
		void onCollectionClick();
	}
	
	public void toSubNews(int theme_id) {
		FragmentTransaction ftt = mFragmentManager.beginTransaction();
		mSubNews = SuberFragment.newInstance(theme_id);
		ftt.replace(R.id.main_content, mSubNews, DRAWER_THEMESUB_TAG).commit();
	}
}
