package com.yx.zhihu;

import java.util.List;
import java.util.zip.Inflater;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import cn.bmob.v3.Bmob;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yx.zhihu.entity.DrawerEntity;
import com.yx.zhihu.utils.ResolutionUtil;
import com.yx.zhihu.utils.Toastor;

public class APP extends Application {
	public static String APPID = "df470c0e32b549db2a41182701fde031";
	
	private static APP app;
	private RequestQueue queue;

	private ResolutionUtil resolution;

	private boolean isLogin = false;

	private DisplayImageOptions options;

	private LayoutInflater mInflate;
	@Override
	public void onCreate() {
		super.onCreate();
		Bmob.initialize(this, APPID);
		
		//����Application
		app = this;
		//�����ʼ��Volley����
//		queue = Volley.newRequestQueue(getApplicationContext(), getExternalCacheDir());
		
		queue = Volley.newRequestQueue(getApplicationContext());
		resolution = new ResolutionUtil(getApplicationContext());
		//ȫ������ImageLoader
        initImageLoader(getApplicationContext());
        toastor = new Toastor(getApplicationContext());
        
        bitmapUtils = new BitmapUtils(this);
        
        mInflate = LayoutInflater.from(this);
	}
	 /** ��ȡApplication */
    public static APP getApp() {
        return app;
    }
    /**
     * ��ȡVolleyʵ�����?
     */
    public RequestQueue getVolleyQueue() {
		return queue;
	}
    public ResolutionUtil getResolution(){
    	return resolution;
    }
    public Toastor getToast(){
    	return toastor;
    }
    public void setLogin(boolean login){
    	isLogin  = login;
    }
    public boolean isLogin() {
		return isLogin;
	}
	
    public void initImageLoader(Context context) {
    	options = new DisplayImageOptions.Builder()  
                .showImageOnLoading(R.drawable.ic_logo)  
                .showImageOnFail(R.drawable.ic_logo)  
                .cacheInMemory(true)  
                .cacheOnDisc(true) 
                .bitmapConfig(Bitmap.Config.RGB_565)  
                .build(); 
    	//创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);  
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);  
    }
    
    public DisplayImageOptions getDefalutOptions(){
        return options;
    }
    
    private List<DrawerEntity> themeLists;

	private Toastor toastor;

	private BitmapUtils bitmapUtils;
    public void setThemeLists(List<DrawerEntity> lists){
    	this.themeLists = lists;
    }
	public List<DrawerEntity> getThemeLists() {
		return themeLists;
	}
	public BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}
	public LayoutInflater getInflate() {
		return mInflate;
	}
	
    
    
	
}
