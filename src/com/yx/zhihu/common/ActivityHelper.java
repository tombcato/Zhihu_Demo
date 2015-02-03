package com.yx.zhihu.common;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.yx.zhihu.activity.ComtActivity;
import com.yx.zhihu.activity.EditorActivity;
import com.yx.zhihu.activity.EditorsActivity;
import com.yx.zhihu.activity.ImageActivity;
import com.yx.zhihu.activity.MainActivity;
import com.yx.zhihu.activity.NewsActivity;
import com.yx.zhihu.entity.ThemeEditerEntity;

/**
 * <activity android:name="com.zhihu.daily.android.activity.MainActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.NewsActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.ThemeActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.LoginActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.CommentActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.SectionActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.EditorActivity_" android:screenOrientation="portrait"/>
        <activity android:configChanges="keyboardHidden|orientation" android:name="com.zhihu.daily.android.activity.CommentComposeActivity_" android:screenOrientation="portrait" android:windowSoftInputMode="stateVisible|adjustResize"/>
        <activity android:configChanges="keyboardHidden|orientation" android:name="com.zhihu.daily.android.activity.SharingComposeActivity_" android:screenOrientation="portrait" android:windowSoftInputMode="adjustResize"/>
        <activity android:name="com.zhihu.daily.android.activity.CommentLikesActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.CommentRepliesActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.ImageViewActivity_" android:screenOrientation="portrait" android:theme="@style/Zhihu.Theme.AppTheme.Overlay"/>
        <activity android:name="com.zhihu.daily.android.activity.EditorsActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.BrowserActivity_" android:screenOrientation="portrait"/>
        <activity android:name="com.zhihu.daily.android.activity.AlertNotificationActivity"/>
        <activity android:name="com.zhihu.daily.android.activity.UriLauncherActivity">
 * @author Amor
 *
 */
public class ActivityHelper {
	
	public static void SplashToHome(Context ct){
		ct.startActivity(new Intent(ct,MainActivity.class));
	}

	public static void HomeToDetail(Context ct, ArrayList<String> ids,int position) {
		Intent intent = new Intent(ct,NewsActivity.class);
		intent.putStringArrayListExtra("ids", ids);
		intent.putExtra("position", position);
		ct.startActivity(intent);
	}

	public static void NewsToComment(Context ct,String id) {
		Intent intent = new Intent(ct,ComtActivity.class);
		intent.putExtra("id", id);
		ct.startActivity(intent);
	}

	public static void NewsToImageDetail(Context ct,String url) {
		Intent intent = new Intent(ct, ImageActivity.class);
		intent.putExtra("imageUrl", url);
		ct.startActivity(intent);
	}

	public static void SuberToEditor(Context ct,ArrayList<ThemeEditerEntity> editors) {
		Intent intent = new Intent(ct, EditorsActivity.class);
		intent.putParcelableArrayListExtra("editors", editors);
		ct.startActivity(intent);
	}

	public static void EditorsToEditor(Context ct, int id) {
		Intent intent = new Intent(ct, EditorActivity.class);
		intent.putExtra("id", id);
		ct.startActivity(intent);
	}

}
