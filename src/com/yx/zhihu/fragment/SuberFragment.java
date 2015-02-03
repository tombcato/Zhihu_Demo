package com.yx.zhihu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import javax.crypto.spec.IvParameterSpec;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.adapter.ThemeNewsAdapter;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.entity.ThemeEditerEntity;
import com.yx.zhihu.entity.ThemeSubEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.utils.ResolutionUtil;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
public class SuberFragment extends BaseFragment implements VolleyCallBack, OnRefreshListener, OnScrollListener, OnItemClickListener{
	private static final int THEME_SUB_DOWN = 1;
	public static SuberFragment newInstance(int id) {
		SuberFragment fragment = new SuberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
		return fragment;
	}
	private MyApiController apiController;
	private PullToRefreshLayout refreshLayout;
	private ListView lv_theme;
	private ThemeNewsAdapter themeNewsAdapter;
	private KenBurnsView kbv_img;
	private TextView tv_intro;
	private TextView tv_soucre;
	private View head;
	private BitmapUtils bitmapUtils;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case THEME_SUB_DOWN:
				ThemeSubEntity info = (ThemeSubEntity)msg.obj;
				processData(info);
				break;
//			case THEME_SUB_BEFORE_DOWN:
//				processBeforeData();
//				break;
			}
		}
	};
	private ResolutionUtil resolution;
	private RelativeLayout rl_iv;
	private int id;
	private List<StoryEntity> stories;
	private ArrayList<ThemeEditerEntity> editors;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		resolution = APP.getApp().getResolution();
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_theme, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}
	
	private void initView(View view) {
		bitmapUtils = new BitmapUtils(getActivity());
		LayoutInflater mInflate = LayoutInflater.from(getActivity());
		head = mInflate.inflate(R.layout.fragment_theme_header, null);
		refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.theme_layout);
		lv_theme = (ListView) view.findViewById(R.id.theme_list_view);
		
		rl_iv = (RelativeLayout) head.findViewById(R.id.theme_image_layout);
		kbv_img = (KenBurnsView) head.findViewById(R.id.theme_head_image);
		tv_intro = (TextView) head.findViewById(R.id.theme_head_intro);
		tv_soucre = (TextView) head.findViewById(R.id.theme_image_source);
		
		lv_theme.setOnScrollListener(this);
		lv_theme.setOnItemClickListener(this);
		ActionBarPullToRefresh.from(getActivity())
		.allChildrenArePullable()
		.listener(this)
		.setup(this.refreshLayout);
	}
	private void initData() {
		apiController = new MyApiController(getActivity(), this);
		stories = new ArrayList<StoryEntity>();
		id = getArguments().getInt("id",-1);
		if(id != -1){
			initSubThemeNews(id);
		}
		
	}
	public void initSubThemeNews(int SubId){
		apiController.getSubThemeNews(ApiConstant.resqCode_ThemeSubNews, SubId);
	}
	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		if(entity == null){
			return;
		}
		switch (resqCode) {
		case ApiConstant.resqCode_ThemeSubNews:
			ThemeSubEntity info = (ThemeSubEntity)entity;
			Logger.e("msg", info.toString()+":");
			Message msg = new Message();
			msg.obj =  info;
			msg.what = THEME_SUB_DOWN;
			mHandler.sendMessage(msg);
			break;

		default:
			break;
		}
	}
	public void processData(ThemeSubEntity info){
		stories.clear();
		String headImg = info.getBackground();
		String description = info.getDescription();
		editors = info.getEditors();
		String image = info.getImage();
		String image_source = info.getImage_source();
		stories = info.getStories();
		
		
		bitmapUtils.display(rl_iv, headImg,new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap bmp,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				kbv_img.setImageBitmap(bmp);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				
			}
		});
		
		tv_intro.setText(description);
		tv_soucre.setText(image_source);
		if(lv_theme.getHeaderViewsCount() > 0){
			lv_theme.removeHeaderView(head);
		}
		lv_theme.addHeaderView(head);
		
		
		if(themeNewsAdapter == null){
			themeNewsAdapter = new ThemeNewsAdapter(getActivity(), lv_theme, stories, editors);
			lv_theme.setAdapter(themeNewsAdapter);
		}else{
			themeNewsAdapter.changeAdapterData(stories);
			themeNewsAdapter.notifyDataSetChanged();
		}
		
		refreshLayout.setRefreshComplete();
	}
	@Override
	public void onRefreshStarted(View view) {
		initSubThemeNews(id);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		 switch (scrollState) {
		    // 当不滚动时
		    case OnScrollListener.SCROLL_STATE_IDLE:
		    // 判断滚动到底部
		    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
		    	getBeforeData(stories.get(stories.size() - 1).getId());
		    	Toast.makeText(getActivity(), "bottom", 0).show();
		     }
		    // 判断滚动到顶部
		    if(view.getFirstVisiblePosition() == 0){
		    }
		     break;
		   } 
	}
	private void getBeforeData(String id) {
		apiController.getSubThemeBefore(ApiConstant.resqCode_ThemeSubBefore, Integer.parseInt(id));
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		if(position == 0) return;
		if(position == 1){
			ActivityHelper.SuberToEditor(getActivity(),editors);
		}else{
			ArrayList<String> ids = new ArrayList<String>();
			for (StoryEntity info : stories) {
				ids.add(info.getId());
			}
			ActivityHelper.HomeToDetail(getActivity(), ids, position - 2);
		}
		
	}
}
