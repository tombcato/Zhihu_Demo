package com.yx.zhihu.fragment;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
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
import com.yx.zhihu.R;
import com.yx.zhihu.activity.NewsActivity;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.StoryDetailEntity;
import com.yx.zhihu.entity.StoryExtraEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.BitmapUtil;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.widget.news.NewsScrollView;
import com.yx.zhihu.widget.news.StoryWebView;

public class NewsFragment extends BaseFragment implements VolleyCallBack, OnRefreshListener {
	protected static final int STORY_DETAIL_DONE = 1;
	protected static final int STORY_EXTRA_DONE = 2;
	private NewsScrollView mScrollView;
	private StoryWebView mWebView;
	private int id;
	private MyApiController myApiController;
	public NewsFragment(){}
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case STORY_DETAIL_DONE:
				StoryDetailEntity info  = (StoryDetailEntity)msg.obj;
				Logger.e("msg", "loadUrl:" + info.getShare_url());
				bitmapUtil.display(iv_img, info.getImage());
				tv_source.setText(info.getImage_source());
				mWebView.loadUrl(info.getShare_url());
				mWebView.loadData(info.getBody(),  "text/html; charset=UTF-8",null);
				break;
			case STORY_EXTRA_DONE:
				//TODO:
				StoryExtraEntity extra = (StoryExtraEntity) msg.obj;
				Logger.e("msg", "extra :" + extra.toString());
				((NewsActivity)getActivity()).setTitleRefresh(extra.isFavorite(), 
															  extra.getVote_status() == 0?false:true, 
															  extra.getComments()+"",
															  extra.getPopularity()+"");
				break;
			default:
				break;
			}
		};
	};
	private ImageView iv_img;
	private BitmapUtils bitmapUtil;
	private TextView tv_source;
	public static NewsFragment newInstance(String string){
		NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", string);
        fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		myApiController = new MyApiController(getActivity(), this);
		bitmapUtil = new BitmapUtils(getActivity());
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_news, container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}
	private void initView(View view) {
		PullToRefreshLayout refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.news_layout);
		mScrollView = (NewsScrollView) view.findViewById(R.id.news_scrollview);
		RelativeLayout rl_newsTop = (RelativeLayout) view.findViewById(R.id.news_top_layout);
		RelativeLayout rl_newsTop_noimg = (RelativeLayout) view.findViewById(R.id.news_top_no_image_layout);
		
		iv_img = (ImageView) view.findViewById(R.id.news_image);
		ImageView iv_img_mask = (ImageView) view.findViewById(R.id.news_image_mask);
		TextView tv_title = (TextView) view.findViewById(R.id.news_title);
		tv_source = (TextView) view.findViewById(R.id.news_image_source);
		mWebView = (StoryWebView) view.findViewById(R.id.news_webview);
		
		LinearLayout rl_news_toolbar = (LinearLayout) view.findViewById(R.id.news_toolbar);
		mWebView.addJavascriptInterface(this,"injectedObject");
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient() {  
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
		
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					mScrollView.requestDisallowInterceptTouchEvent(false);
				}else{
					mScrollView.requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});
        ActionBarPullToRefresh.from(getActivity())
		.allChildrenArePullable()
		.listener(this)
		.setup(refreshLayout);
	}
	
//	public void initData(StoryEntity info){
//		Logger.e("msg", info+":url");
//		mWebView.loadUrl(info.getShare_url());
//	}
	public void initData(){
//		Logger.e("msg", info+":url");
		String id = getArguments().getString("id");
		Logger.e("msg", id+":id");
		Toast.makeText(getActivity(), id + ":", 0).show();
		myApiController.getStoryDetailData(ApiConstant.resqCode_StoryDetail, id);
		myApiController.getStoryExtraData(ApiConstant.resqCode_StoryEXTRA, id);
	}
	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		if(entity == null){
			Toast.makeText(getActivity(), "请求数据为空", 0).show();
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
	
	@JavascriptInterface
	public void openImage(String url){
		ActivityHelper.NewsToImageDetail(getActivity(),url);
	}
	@Override
	public void onRefreshStarted(View view) {
	}
	
}
