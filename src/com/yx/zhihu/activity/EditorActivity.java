package com.yx.zhihu.activity;

import com.yx.zhihu.common.ApiConstant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class EditorActivity extends BaseActivity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebView = new WebView(this);
		setContentView(mWebView,new LayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT));
		
		initView();
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
		if(intent != null){
			int id = intent.getIntExtra("id", 0);
			mWebView.loadUrl(String.format(ApiConstant.API_EDITOR_INFO, id));
		}
	}

	private void initView() {
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
    		public boolean shouldOverrideUrlLoading(WebView view, String url) {
    			Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
    			startActivity(intent);
    			return true;
    		} 
		});
		mWebView.setWebChromeClient(new WebChromeClient());
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
}
