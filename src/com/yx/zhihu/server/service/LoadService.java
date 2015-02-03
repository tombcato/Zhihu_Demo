package com.yx.zhihu.server.service;

import android.app.IntentService;
import android.content.Intent;

import com.yx.zhihu.server.net.HttpTaskManager;


/**
 * 此服务主要是 预加载数据,加载完成后通过广播通知
 * 
 * @author youxiang
 * 
 */
public class LoadService extends IntentService {

	private static final String TAG = LoadService.class.getSimpleName();
	public LoadService() {
		super(TAG);
		initDevice();
	}
	
	public void initDevice(){
		HttpTaskManager.getInstance().submit(new Runnable() {
			@Override
			public void run() {
//				mDevice = DeviceFactory.getInstance().getDevice();
//				mDevice.initDeviceInfo(getApplicationContext());
//				System.out.println("BlueLight:  initDevice...completed...............................");
			}
		});
	}
	/**
	 * 异步请求网络数据
	 */
	@Override
	protected void onHandleIntent(Intent arg0) {
		
		loadFocusList();		

	}
	
	private void loadFocusList() {
		
	}
	
}
