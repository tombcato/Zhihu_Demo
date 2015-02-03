package com.yx.zhihu.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.SplashEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.utils.ResolutionUtil;

public class SplashActivity extends BaseActivity implements VolleyCallBack {
	
	private static final String TAG = SplashActivity.class.getSimpleName();
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			 ActivityHelper.SplashToHome(SplashActivity.this);
		     finish(); 
		};
	};
	protected UpdateResponse ur;
	private ImageView iv_splash;
	private String imgDesc;
	private String imgUrl;
	private TextView tv_imgDesc;
	private ImageView iv_logo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//在你需要调用自动更新功能之前先进行初始化建表操作
		BmobUpdateAgent.initAppVersion(this);
		
		ResolutionUtil resolution = APP.getApp().getResolution();
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.hide();
		
		iv_splash = (ImageView) findViewById(R.id.iv_splash);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		tv_imgDesc = (TextView) findViewById(R.id.tv_img_desc);
		
		tv_imgDesc.setTextSize(resolution.px2sp2px(22));
		
		RelativeLayout.LayoutParams logoParams = (LayoutParams) iv_logo.getLayoutParams();
		RelativeLayout.LayoutParams tvParams = (LayoutParams) tv_imgDesc.getLayoutParams();
		tvParams.topMargin = resolution.px2dp2px(10, false);
		
		initUpdate();
		initData();
		
		
	}
	private void initData() {
		MyApiController apiControl = new MyApiController(this, this);
		apiControl.getSplashData(ApiConstant.resqCode_splash);
	}
	private void initUpdate() {
		BmobUpdateAgent.update(this);
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		BmobUpdateAgent.initAppVersion(this);
		BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
			
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				if (updateStatus == UpdateStatus.Yes) {
					ur = updateInfo;
					Logger.i("update", updateInfo.path + ":" + updateInfo.version);
				}else{
//					mHandler.sendEmptyMessageDelayed(0, 3000);
				}
			}
		});
	}
	
//	public <T> void VolleyloadDone(ApiResult<T> apiResult) {
//		if(apiResult.exception != null ){
//			Logger.e(TAG, "请求splash error");
//			return;
//		}
//		
//		List<SplashEntity> dataList = (List<SplashEntity>) apiResult.dataList;
//		if(dataList != null && dataList.size() > 0){
//			imgDesc = dataList.get(0).getText();
//			imgUrl = dataList.get(0).getImg();
//		}
//		
//		processData();
//	}
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
				Animation anim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash);
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
						mHandler.sendEmptyMessageDelayed(0, 0);
					}
				});
				
				view.startAnimation(anim);
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				
			}
		});

	}
	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		SplashEntity info = (SplashEntity)entity;
		imgDesc = info.getText();
		imgUrl = info.getImg();
		processData();
	}
		

}
