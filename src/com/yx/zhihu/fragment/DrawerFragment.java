package com.yx.zhihu.fragment;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.activity.MainActivity.ClickCallBack;
import com.yx.zhihu.adapter.DrawerMenuAdapter;
import com.yx.zhihu.entity.DrawerEntity;
import com.yx.zhihu.server.receiver.BroadcastController;
import com.yx.zhihu.view.CircleImageView;

/**
 * 导航菜单栏
 * @created 2014-04-29
 * @author 火蚁（http://my.oschina.net/LittleDY）
 * 
 */
public class DrawerFragment extends Fragment implements OnClickListener, OnItemClickListener {

	public static DrawerFragment newInstance() {
		return new DrawerFragment();
	}
	
	private CircleImageView mUser_info_userface;
	private LinearLayout mMenu_func_favorite;
	private LinearLayout mMenu_func_msg;
	private LinearLayout mMenu_func_setting;
	private ListView mMenu_listview;
	private RelativeLayout mMenu_func_offline;
	private RelativeLayout mMenu_func_darkMode;
	private RelativeLayout mMenu_info_loginbg;
	private TextView mUser_info_username;
//	private DrawerMenuCallBack mCallBack;
	private DrawerMenuAdapter drawerMenuAdapter;
	private APP app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mApplication = (AppContext) getActivity().getApplication();
		app = APP.getApp();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_navigation_drawer, container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		setupUserView(APP.getApp().isLogin());
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		if (activity instanceof DrawerMenuCallBack) {
//			mCallBack = (DrawerMenuCallBack) activity;
//		}
		// 注册一个用户发生变化的广播
		BroadcastController.registerUserChangeReceiver(activity, mUserChangeReceiver);
	}
	
	
	@Override
	public void onDetach() {
		super.onDetach();
//		mCallBack = null;
		// 注销接收用户信息变更的广播
		BroadcastController.unregisterReceiver(getActivity(), mUserChangeReceiver);
	}
	
	private BroadcastReceiver mUserChangeReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			//接收到变化后，更新用户资料
			setupUserView(true);
		}
	};
//
	private ClickCallBack menuCallBack;
	

//	
	private void setupUserView(final boolean reflash) {
		//判断是否已经登录，如果已登录则显示用户的头像与信息
//		if(!mApplication.isLogin()) {
//			mUser_info_userface.setImageResource(R.drawable.mini_avatar);
//			mUser_info_username.setText("");
//			mMenu_user_info_layout.setVisibility(View.GONE);
//			mMenu_user_login_tips.setVisibility(View.VISIBLE);
//			return;
//		}
//		
//		mMenu_user_info_layout.setVisibility(View.VISIBLE);
//		mMenu_user_login_tips.setVisibility(View.GONE);
//		mUser_info_username.setText("");
//		
//		new AsyncTask<Void, Void, User>() {
//			
//			@Override
//			protected User doInBackground(Void... params) {
//				User user = mApplication.getLoginInfo();
//				return user;
//			}
//			
//			@Override
//			protected void onPostExecute(User user) {
//				if(user == null || isDetached()) {
//					return;
//				}
//				// 加载用户头像
//				String portrait = user.getPortrait() == null || user.getPortrait().equals("null") ? "" : user.getPortrait();
//				if (portrait.endsWith("portrait.gif") || StringUtils.isEmpty(portrait)) {
//					mUser_info_userface.setImageResource(R.drawable.widget_dface);
//				} else {
//					String faceUrl = URLs.HTTPS + URLs.HOST + URLs.URL_SPLITTER + user.getPortrait();
//					UIHelper.showUserFace(mUser_info_userface, faceUrl);
//				}
//				// 其他资料
//				mUser_info_username.setText(user.getName());
//			}
//			
//		}.execute();
	}
	
	// 初始化界面控件
	private void initView(View view) {
		mMenu_info_loginbg = (RelativeLayout) view.findViewById(R.id.drawer_user);
		mUser_info_userface = (CircleImageView) view.findViewById(R.id.drawer_user_avatar);
		mUser_info_username = (TextView) view.findViewById(R.id.drawer_user_name);

		mMenu_func_favorite = (LinearLayout) view.findViewById(R.id.drawer_favorite);
		mMenu_func_msg = (LinearLayout) view.findViewById(R.id.drawer_notification);
		mMenu_func_setting = (LinearLayout) view.findViewById(R.id.drawer_setting);
		
		mMenu_listview = (ListView) view.findViewById(R.id.drawer_list_view);
		
		mMenu_func_offline = (RelativeLayout) view.findViewById(R.id.drawer_offline);
		mMenu_func_darkMode = (RelativeLayout) view.findViewById(R.id.drawer_dark_mode);
		
		// 绑定点击事件
		mMenu_info_loginbg.setOnClickListener(this);
		mMenu_func_favorite.setOnClickListener(this);
		mMenu_func_msg.setOnClickListener(this);
		mMenu_func_setting.setOnClickListener(this);
		mMenu_func_offline.setOnClickListener(this);
		mMenu_func_darkMode.setOnClickListener(this);

		mMenu_listview.setOnItemClickListener(this);
		
	}
	
	public void setData(List<DrawerEntity> drawers) {
		// 初始化频道listview
		if(drawerMenuAdapter == null){
			drawerMenuAdapter = new DrawerMenuAdapter(getActivity(),drawers);
			mMenu_listview.setAdapter(drawerMenuAdapter);
		}else{
			drawerMenuAdapter.changeAdapterData(drawers);
			drawerMenuAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.drawer_user:
			onClickLogin();
			break;
		case R.id.drawer_favorite:
			onClickFavorite();
			break;
		case R.id.drawer_notification:
			onClickMessage();
			break;
		case R.id.drawer_setting:
			onClickSetting();
			break;
		case R.id.drawer_offline:
			onClickoffline();
			break;
		case R.id.drawer_dark_mode:
			onClickDarkMode();
			break;
		}
	}
	
	private void onClickDarkMode() {
		
	}
	private void onClickoffline() {
		
	}
	private void onClickSetting() {
		
	}
	private void onClickMessage() {
		
	}
	private void onClickFavorite() {
		
	}
	private void onClickLogin() {
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
		menuCallBack.onThemeListClick(position);
	}
	
	public void setMenuCallBack(ClickCallBack callback){
		this.menuCallBack = callback;
	}
	
}
