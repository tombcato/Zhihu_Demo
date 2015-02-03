package com.yx.zhihu.activity;

import com.yx.zhihu.R;
import com.yx.zhihu.R.layout;
import com.yx.zhihu.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ComtActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comt, menu);
		return true;
	}

}
