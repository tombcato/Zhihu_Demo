package com.yx.zhihu.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;


public class BaseActivity extends ActionBarActivity {
	private boolean isDarkTheme;
	protected LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(this);
		isDarkTheme = mPerferences.getBoolean("dark_theme?", false); 
		mInflater = LayoutInflater.from(this);
	}
}
