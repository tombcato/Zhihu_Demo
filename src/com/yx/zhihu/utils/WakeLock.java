package com.yx.zhihu.utils;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * <!-- 亮屏 -->
 * require <uses-permission android:name="android.permission.WAKE_LOCK"/>
 *
 * @author MaTianyu
 * @date 2014-11-04
 */
public class WakeLock {
    PowerManager          pm;
    PowerManager.WakeLock wakeLock;

    public WakeLock(Context context, String tag) {
        ////获取电源的服务 声明电源管理器
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, tag);
    }

    /**
     * Call requires API level 7
     */
    public boolean isScreenOn() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR_MR1) {
            Logger.e("Logger : ", "can not call isScreenOn if SDK_INT < 7 ");
            return false;
        } else {
            return pm.isScreenOn();
        }
    }

    public void turnScreenOn() {
        //点亮亮屏
        Logger.i("Logger : ", "PowerManager.WakeLock : wakeLock.isHeld: " + wakeLock.isHeld());
        if (!wakeLock.isHeld()) {
            Logger.i("Logger : ", "PowerManager.WakeLock : 点亮屏幕");
            wakeLock.acquire();
        }
    }

    public void turnScreenOff() {
        //释放亮屏
        Logger.i("Logger : ", "PowerManager.WakeLock : wakeLock.isHeld: " + wakeLock.isHeld());
        if (wakeLock.isHeld()) {
            Logger.i("Logger : ", "PowerManager.WakeLock : 灭掉屏幕");
            wakeLock.release();
        }
    }

    protected void release() {
        if (wakeLock != null) {
            wakeLock.release();
        }
    }
}
