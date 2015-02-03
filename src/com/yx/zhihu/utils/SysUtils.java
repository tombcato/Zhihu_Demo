package com.yx.zhihu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

/**
 * ϵͳutils
 * 
 * @author QIYI - wangfengmin
 * 
 */
public final class SysUtils {
	private static final String TAG = "SysUtils";
	
	/*  
	 * 获取当前程序的版本号   
	 */   
	public static String getVersion(Context ctx){
		String version ="1.0";
		try{
			PackageManager manager =ctx.getPackageManager();
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			version = info.versionName;
			return version;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return version;
	}
	
	
	/**
	 * �豸apikey
	 */
	public static String apiKey;
	
	/**
	 * mac��ַ
	 */
	public static String mac;
	
	/**
	 * ����ʱ���ϵͳʱ��֮��
	 */
	public static long deltaTime = 0;
	
	/**
	 * �˳��ͻ���
	 */
	public static void exitApp(final Activity activity) {
//		activity.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * ��ȡ��ǰϵͳʱ��
	 * @return
	 */
	public static Long getSysTime(){
		long now =  System.currentTimeMillis() + deltaTime;
		
		return now;
	}
	
	/**
	 *  ����app
	 * 
	 */
	public static void restartAPP(final Context context) {
		ActivityManager activityMgr = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		activityMgr.restartPackage(context.getPackageName());
	}
	
	/**
	 * liuzm
	 * 
	 * ��ȡ�ļ�MAC��ַ /sys/class/net/eth0/address
	 * 
	 * @param fileName
	 *            �ж� \r\n
	 */
	public static String getEthMAC() {
		Reader reader = null;
		StringBuffer str = new StringBuffer();
		try {
			char[] tempchars = new char[20];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(
					"/sys/class/net/eth0/address"));
			while ((charread = reader.read(tempchars)) != -1) {
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							str.append(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e1) {
			Logger.e(TAG, e1.getMessage());
		} finally {
			if (reader != null) 
				try {
					reader.close();
				} catch (Exception e1) {	}
		}

		return str.toString().trim();
	}
	
	/**
	 * ��ȡ������mac��ַ
	 *  
	 */
	public static String getWifiMAC(Context ctx) {
		try{
			String macAddress = null;
			WifiManager wifiMgr = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) 
			    macAddress = info.getMacAddress();
			
			return macAddress;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * ��ȡdataĿ¼ʣ��ռ�
	 * @return
	 */
	public static long getDataSpareQuantity(){
		File data = Environment.getDataDirectory();
		StatFs sf = new StatFs(data.getAbsolutePath()); 
		long blockSize = sf.getBlockSize(); 
		long availCount = sf.getAvailableBlocks(); 
		   
		return blockSize*availCount-1024*1024*2;
	}
	
	/**
	 * ��ȡSD��ʣ��ռ�
	 * @return
	 */
	public static long getSDCardSpareQuantity(){
		
		String state = Environment.getExternalStorageState(); 
        if(Environment.MEDIA_MOUNTED.equals(state)) { 
            File sdcardDir = Environment.getExternalStorageDirectory(); 
            StatFs sf = new StatFs(sdcardDir.getPath()); 
            long blockSize = sf.getBlockSize(); 
            long availCount = sf.getAvailableBlocks(); 
           
            return blockSize*availCount-1024*1024*2;
        }    
        return -1;
   }

	/**
	 * 获取固件版本
	 * @param ctx
	 * @return
	 */
	public static String getFirmver(Context ctx){
		return Build.VERSION.RELEASE;
	}
	
	
	/**
	 * SD卡是否存在
	 * @return
	 */
	public static boolean hasSDCard(){
		final String state = Environment.getExternalStorageState();  
        if (state.equals(Environment.MEDIA_MOUNTED)) {  
            return true;  
        } else {  
            return false;  
        }  
	}
	
	/**
     * whether this process is named with processName
     * 
     * @param context
     * @param processName
     * @return <ul>
     *         return whether this process is named with processName
     *         <li>if context is null, return false</li>
     *         <li>if {@link ActivityManager#getRunningAppProcesses()} is null, return false</li>
     *         <li>if one process of {@link ActivityManager#getRunningAppProcesses()} is equal to processName, return
     *         true, otherwise return false</li>
     *         </ul>
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }

        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null) {
            return true;
        }

        for (RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid && processName.equals(processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     * 
     * @param context
     * @return if application is in background return true, otherwise return false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
