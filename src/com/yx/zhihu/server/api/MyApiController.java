package com.yx.zhihu.server.api;


import android.content.Context;

import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.BeforeEntity;
import com.yx.zhihu.entity.LaststEntity;
import com.yx.zhihu.entity.SplashEntity;
import com.yx.zhihu.entity.StoryDetailEntity;
import com.yx.zhihu.entity.StoryExtraEntity;
import com.yx.zhihu.entity.ThemeEntity;
import com.yx.zhihu.entity.ThemeSubEntity;
import com.yx.zhihu.server.net.MyVolley;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;

public class MyApiController {
	
	
	private static final boolean CacheRequestFlag = true;
	private MyVolley myVolley;
	private Context mContext;

	public MyApiController(Context ctx, VolleyCallBack callback){
		this.mContext = ctx;
		myVolley = new MyVolley(ctx, callback);
	}
	/**
	 * 获取splash数据
	 * @param requestCode
	 */
	public void getSplashData(int requestCode){
		String url = String.format(ApiConstant.API_SPLASH_DATA);
		myVolley.volleyGet(requestCode, url, SplashEntity.class , CacheRequestFlag);
	}
	
	public void getLastData(int requestCode, boolean isCacheRequest) {
		String url = String.format(ApiConstant.API_HOME_STORY_LAST);
		myVolley.volleyGet(requestCode, url, LaststEntity.class, isCacheRequest);
	}
	public void getThemeLists(int requestCode) {
		String url = String.format(ApiConstant.API_MENU_THEMES);
		myVolley.volleyGet(requestCode, url, ThemeEntity.class, CacheRequestFlag);
	}
	public void getSubThemeNews(int requestCode,int id) {
		String url = String.format(ApiConstant.API_SUBTHEME_DATA,id);
		myVolley.volleyGet(requestCode, url, ThemeSubEntity.class, CacheRequestFlag);
	}
	public void getSubThemeBefore(int requestCode, int id){
		String url = String.format(ApiConstant.API_SUBTHEME_BEFORE_DATA,id);
		myVolley.volleyGet(requestCode, url, ThemeSubEntity.class, CacheRequestFlag);
	}
	public void getAddSubTheme(int requestCode,int id) {
		String url = String.format(ApiConstant.API_SUBSCRIBE_ADD,id);
		myVolley.volleyGet(requestCode, url, null, CacheRequestFlag);
	}
	public void getBeforeData(int requestCode, String date) {
		String url = String.format(ApiConstant.API_HOME_STORY_BEFORE,date);
		myVolley.volleyGet(requestCode, url, BeforeEntity.class, CacheRequestFlag);
	}
	public void getStoryDetailData(int requestCode, String id) {
		String url = String.format(ApiConstant.API_STORY_DETAIL,id);
		myVolley.volleyGet(requestCode, url, StoryDetailEntity.class, CacheRequestFlag);
	}
	public void getStoryExtraData(int requestCode, String id) {
		String url = String.format(ApiConstant.API_STORY_EXTRA,id);
		myVolley.volleyGet(requestCode, url, StoryExtraEntity.class, CacheRequestFlag);
	}
	
	
//	/**
//	 * 模拟get请求
//	 * @param params
//	 */
//	public void getHomePicData(int resquestCode,String params){
//		String url = String.format("http://api.cstv.hiveview.com:80/user/friends/%s", params);
//		myVolley.volleyGet(resquestCode, url, HomePicEntity.class);
//	}
//	
//	/**
//	 * 模拟post请求
//	 * @param params
//	 */
//	public void getHomePicData2(int resquestCode,String params){
//		String url = String.format("http://api.cstv.hiveview.com:80/user/friends/%s", params);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("city", "beijing");
//		myVolley.volleyPost(resquestCode, url, HomePicEntity.class, map);
//	}
//	
//	class HomePicEntity{
//		private String picId;
//		private String picUrl;
//		public String getPicId() {
//			return picId;
//		}
//		public void setPicId(String picId) {
//			this.picId = picId;
//		}
//		public String getPicUrl() {
//			return picUrl;
//		}
//		public void setPicUrl(String picUrl) {
//			this.picUrl = picUrl;
//		}
//		
//	}
	
}
