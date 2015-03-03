package com.yx.zhihu.widget.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.activity.ImageActivity;
import com.yx.zhihu.activity.NewsActivity;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.common.AppConstant;
import com.yx.zhihu.common.AssetsUtils;
import com.yx.zhihu.entity.StoryDetailEntity;
import com.yx.zhihu.entity.StoryExtraEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.utils.ResolutionUtil;

@SuppressLint("SetJavaScriptEnabled")
public class NewsDetialWidget extends RelativeLayout implements VolleyCallBack, OnRefreshListener{
	protected static final int STORY_DETAIL_DONE = 1;
	protected static final int STORY_EXTRA_DONE = 2;
	private View view;
	private MyApiController myApiController;
	private BitmapUtils bitmapUtil;
	private NewsScrollView mScrollView;
	private ImageView iv_img;
	private TextView tv_source;
	private StoryWebView mWebView;
	private TextView tv_title;
	private ResolutionUtil resolution;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case STORY_DETAIL_DONE:
				StoryDetailEntity info  = (StoryDetailEntity)msg.obj;
				Logger.e("msg", "loadUrl:" + info.getShare_url());
				processData(info);
				break;
			case STORY_EXTRA_DONE:
				//TODO:
				StoryExtraEntity extra = (StoryExtraEntity) msg.obj;
				Logger.e("msg", "extra :" + extra.toString());
				((NewsActivity)getContext()).setTitleRefresh(extra.isFavorite(), 
															  extra.getVote_status() == 0?false:true, 
															  extra.getComments()+"",
															  extra.getPopularity()+"");
				break;
			default:
				break;
			}
		};
	};
	

	public NewsDetialWidget(Context context) {
		super(context);
		init();
	}

	public NewsDetialWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NewsDetialWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	

	private void init() {
		LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.fragment_news, null);
		
		addView(view);
		initView();
		setListener();
	}

	private void initView() {
		APP app = APP.getApp();
		resolution = app.getResolution();
		myApiController = new MyApiController(getContext(), this);
		bitmapUtil = new BitmapUtils(getContext());
		
		PullToRefreshLayout refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.news_layout);
		mScrollView = (NewsScrollView) view.findViewById(R.id.news_scrollview);
		RelativeLayout rl_newsTop = (RelativeLayout) view.findViewById(R.id.news_top_layout);
		RelativeLayout rl_newsTop_noimg = (RelativeLayout) view.findViewById(R.id.news_top_no_image_layout);
		
		iv_img = (ImageView) view.findViewById(R.id.news_image);
		ImageView iv_img_mask = (ImageView) view.findViewById(R.id.news_image_mask);
		tv_title = (TextView) view.findViewById(R.id.news_title);
		tv_source = (TextView) view.findViewById(R.id.news_image_source);
		mWebView = (StoryWebView) view.findViewById(R.id.news_webview);
		
		LinearLayout rl_news_toolbar = (LinearLayout) view.findViewById(R.id.news_toolbar);
		
		ActionBarPullToRefresh.from((NewsActivity)getContext())
		.allChildrenArePullable()
		.listener(this)
		.setup(refreshLayout);
		
		WebSettings settings = mWebView.getSettings();
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        
        mWebView.addJavascriptInterface(this, "injectedObject");
        
        mWebView.setWebViewClient(new WebViewClient(){
        	@Override
    		public boolean shouldOverrideUrlLoading(WebView view, String url) {
    			Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
    			getContext().startActivity(intent);
    			return true;
    		} 
        });
		mWebView.setWebChromeClient(new WebChromeClient() {  
			@Override
			public boolean onJsAlert(WebView view, String url, String message,JsResult result) {
				result.cancel();
				return super.onJsAlert(view, url, message, result);
			}
            public void onProgressChanged(WebView view, int progress) {  
                // Activity和Webview根据加载程度决定进度条的进度大小  
                // 当加载到100%的时候 进度条自动消失  
//            	pb_loading.setProgress(progress);
//            	Logger.i("msg", progress + "");
//            	if (progress >= 90) {
//					pb_loading.setVisibility(View.GONE);
//				}
////             EasyreadActivity.this.setProgress(progress * 100);  
            }  
        });
	}
	
	public void setData(String id){
		Logger.e("msg", id+":id");
		Toast.makeText(getContext(), id + ":", 0).show();
		myApiController.getStoryDetailData(ApiConstant.resqCode_StoryDetail, id);
//		myApiController.getStoryExtraData(ApiConstant.resqCode_StoryEXTRA, id);
	}

	private void setListener() {
		
	}

	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		if(entity == null){
			Toast.makeText(getContext(), "请求数据为空", 0).show();
			return;
		}
		Message msg = null;
		switch (resqCode) {
		case ApiConstant.resqCode_StoryDetail:
			msg = Message.obtain();
			msg.what = STORY_DETAIL_DONE;
			msg.obj = (StoryDetailEntity)entity;
			mHandler.sendMessage(msg);
			break;
		case ApiConstant.resqCode_StoryEXTRA:
		    msg = Message.obtain();
			msg.what = STORY_EXTRA_DONE;
			msg.obj = (StoryExtraEntity)entity;
			mHandler.sendMessage(msg);
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		
	}
	

	public void processData(StoryDetailEntity info) {
		bitmapUtil.display(iv_img, info.getImage());
		tv_title.setText(info.getTitle());
		tv_source.setText(info.getImage_source());
		
		String html = AssetsUtils.loadText(getContext(), AppConstant.TEMPLATE_DEF_URL);
		html = html.replace("{content}", info.getBody() + "");
		html = html.replace("{nightTheme}","false");
		html = replaceImgTagFromHTML(html);
		
		mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
	}
	
	/**
	 * 替换html中的<img标签的属性
	 * 添加点击事件
	 * @param html
	 * @return
	 */
	private String replaceImgTagFromHTML(String html) {
		
		Document doc = Jsoup.parse(html);

		Elements es = doc.getElementsByTag("img");

		for (Element e : es) {
			
			String imgUrl = e.attr("src");
			
			if (!e.attr("class").equals("avatar") ) {
				e.attr("onclick", "openImage('" + imgUrl + "')");
			}
		}
		return doc.html();
	}
	
	@JavascriptInterface
	public void openImage(String url){
		Toast.makeText(getContext(), "openImage", 0).show();
		ActivityHelper.NewsToImageDetail(getContext(),url);
	}
	

}
