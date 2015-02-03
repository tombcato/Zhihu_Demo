package com.yx.zhihu.fragment;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.StoryDetailEntity;
import com.yx.zhihu.entity.StoryExtraEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.BitmapUtil;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.widget.news.NewsScrollView;
import com.yx.zhihu.widget.news.StoryWebView;

public class TestFragment extends BaseFragment implements VolleyCallBack {
	protected static final int STORY_DETAIL_DONE = 1;
	protected static final int STORY_EXTRA_DONE = 2;
	private NewsScrollView mScrollView;
	private StoryWebView mWebView;
	private int id;
	private MyApiController myApiController;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case STORY_DETAIL_DONE:
				StoryDetailEntity info  = (StoryDetailEntity)msg.obj;
				Logger.e("msg", "loadUrl:" + info.getShare_url());
				tv_test.setText(info.getBody());
				bitmapUtil.display(iv_img, info.getImage());
				break;
			case STORY_EXTRA_DONE:
				//TODO:
				break;
			default:
				break;
			}
		};
	};
	private ImageView iv_img;
	private BitmapUtils bitmapUtil;
	private TextView tv_source;
	private TextView tv_test;
	private ImageView iv_test;
	public static TestFragment newInstance(String string){
		TestFragment fragment = new TestFragment();
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
		return inflater.inflate(R.layout.test, container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}
	private void initView(View view) {
		tv_test = (TextView) view.findViewById(R.id.tv_test);
		iv_test = (ImageView) view.findViewById(R.id.iv_test);
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
	
}
