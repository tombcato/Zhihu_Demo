package com.yx.zhihu.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yx.zhihu.R;
import com.yx.zhihu.adapter.EditorsShowAdapter;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.entity.ThemeEditerEntity;

public class EditorsActivity extends BaseActivity implements OnItemClickListener {
	private ListView lv_editors;
	private ArrayList<ThemeEditerEntity> editors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editors);
		initView();
		initData();
	}

	private void initView() {
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setTitle("主编");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		lv_editors = (ListView) findViewById(R.id.lv_editors);
		lv_editors.setDividerHeight(0);
		lv_editors.setOnItemClickListener(this);
	}
	
	@SuppressWarnings("null")
	private void initData() {
		Intent intent = getIntent();
		if(intent != null){
			editors = intent.getParcelableArrayListExtra("editors");
			EditorsShowAdapter editorsAdapter = new EditorsShowAdapter(this, lv_editors, editors);
			lv_editors.setAdapter(editorsAdapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
		ActivityHelper.EditorsToEditor(this,editors.get(pos).getId());
	}
}
