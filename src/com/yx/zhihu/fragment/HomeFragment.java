package com.yx.zhihu.fragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.activity.MainActivity;
import com.yx.zhihu.adapter.HomeNewsAdapter3;
import com.yx.zhihu.common.ActivityHelper;
import com.yx.zhihu.common.ApiConstant;
import com.yx.zhihu.common.DateUtils;
import com.yx.zhihu.common.ZhihuUtils;
import com.yx.zhihu.entity.BeforeEntity;
import com.yx.zhihu.entity.HomeEntity;
import com.yx.zhihu.entity.LaststEntity;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.server.api.MyApiController;
import com.yx.zhihu.server.net.MyVolley.VolleyCallBack;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.utils.Toastor;
import com.yx.zhihu.view.PinnedSectionListView;
import com.yx.zhihu.view.PinnedSectionListView.SectionChangeListener;
import com.yx.zhihu.widget.home.HomeHeadWidget;

public class HomeFragment extends BaseFragment implements VolleyCallBack, OnScrollListener, OnRefreshListener, OnItemClickListener, SectionChangeListener {
	public static HomeFragment newInstance() {
		return new HomeFragment();
	}
	private static final int CHANGE = 1;
	private static final int LAST_DATA_DONE = 2;
	private static final int BEFORE_DATA_DONE = 3;
	private static final int FLAG_PULLDOWN = 10;
	private static final int FLAG_PULLUP = 11;
	private static final int REFRESH_FINISH = 12;
	private static final int REFRESH_DATA = 13;
	private APP app;
	private String date;
	private Toastor toastor;
	private int refreshFlag;
	private HomeEntity homeEntity;
	private HomeNewsAdapter3 newsAdapter;
	private BeforeEntity beforeEntity;
	private MyApiController apiControll;
	private List<BeforeEntity> beforeLists;
	private List<StoryEntity> news;
	private List<StoryEntity> top_stories;
	private PullToRefreshLayout refreshLayout;
	private HomeHeadWidget homeHeadWidget;
	private boolean isRefreshing = false;
	private PinnedSectionListView pinnedLv;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LAST_DATA_DONE:
				processHeadData();
				processNewsData();
				mHandler.sendEmptyMessageDelayed(REFRESH_FINISH, 1000);
				break;
			case BEFORE_DATA_DONE:
				processNewsData();
				break;
			case REFRESH_FINISH:
				refreshLayout.setRefreshComplete();
				break;
			case REFRESH_DATA:
				initData(false);
			default:
				break;
			}
		};
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = APP.getApp();
		apiControll = new MyApiController(app.getApplicationContext(), this);
		homeEntity = new HomeEntity();
		news = new ArrayList<StoryEntity>();
		beforeEntity = new BeforeEntity();
		beforeLists = new ArrayList<BeforeEntity>();
		toastor = app.getToast();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container,false);
	}
	
	@Override
	public void onDestroyView() {
		mHandler.removeCallbacksAndMessages(null);
		super.onDestroyView();
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		//先走缓存initData，再延时刷新网络数据
		initData(true);
		mHandler.sendEmptyMessageDelayed(REFRESH_DATA,3000);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	public void initData(boolean isCacheRequest) {
		if(!isRefreshing){
			refreshFlag = FLAG_PULLDOWN;//下拉刷新
			isRefreshing = true;
			apiControll.getLastData(ApiConstant.resqCode_lastData,isCacheRequest);
		}
	}
	
	public void getBeforeData(String date){
		if(!isRefreshing){
			refreshFlag = FLAG_PULLUP;//上拉加载
			isRefreshing = true;
			apiControll.getBeforeData(ApiConstant.resqCode_beforeData,date);
		}
	}
	
	private void initView(View view){
		refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.home_layout);
		pinnedLv = (PinnedSectionListView) view.findViewById(R.id.home_listview);
		pinnedLv.setShadowVisible(false);
		pinnedLv.setPinnedStatus(false);
		pinnedLv.setOnItemClickListener(this);
		pinnedLv.setOnScrollListener(this);
		pinnedLv.setOnSectionChangeListener(this);
		
        ActionBarPullToRefresh.from(getActivity())
		.allChildrenArePullable()
		.listener(this)
		.setup(this.refreshLayout);
	}
	private void processHeadData(){
		if(homeHeadWidget == null){
			homeHeadWidget = new HomeHeadWidget(getActivity());
		}
		if(pinnedLv.getHeaderViewsCount() > 0){
			pinnedLv.removeHeaderView(homeHeadWidget);
			homeHeadWidget = new HomeHeadWidget(getActivity());
		}
		homeHeadWidget.setData(top_stories);
		pinnedLv.addHeaderView(homeHeadWidget);
	}
	private void processNewsData(){
		if(newsAdapter == null){
			newsAdapter = new HomeNewsAdapter3(getActivity(),news);
			pinnedLv.setAdapter(newsAdapter);
		}else{
			newsAdapter.changeAdapterData(news);
			newsAdapter.notifyDataSetChanged();
		}
	}
	
	
	@Override
	public <T> void VolleyloadDone(int resqCode, T entity) {
		isRefreshing = false;
		if(entity == null){
			toastor.getSingletonToast("获取首页数据失败");
			return;
		}
		
		switch (resqCode) {
		case ApiConstant.resqCode_lastData:
			news.clear();
			LaststEntity lastData = (LaststEntity)entity;
			List<StoryEntity> stories = lastData.getStories();
			top_stories = lastData.getTop_stories();
			date = lastData.getDate();
			StoryEntity info = new StoryEntity();
			info.setDate(date);
			info.setId("");
			info.setTYPE(StoryEntity.SECTION);
			stories.add(0, info);
			news.addAll(stories);
			mHandler.sendEmptyMessage(LAST_DATA_DONE);
			break;
		case ApiConstant.resqCode_beforeData:
			BeforeEntity ent = (BeforeEntity)entity;
			List<StoryEntity> stories2 = ent.getStories();
			date = ent.getDate();
			StoryEntity info2 = new StoryEntity();
			info2.setDate(date);
			info2.setId("");
			info2.setTYPE(StoryEntity.SECTION);
			stories2.add(0, info2);
			news.addAll(stories2);
			Log.e("msg", news.toString() + ":");
			mHandler.sendEmptyMessage(BEFORE_DATA_DONE);
			break;
			
		default:
			break;
		}
		mHandler.sendEmptyMessage(CHANGE);
	}
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		   switch (scrollState) {
		    // 当不滚动时
		    case OnScrollListener.SCROLL_STATE_IDLE:
		    // 判断滚动到底部
		    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
		    	getBeforeData(date);
		     }
		    // 判断滚动到顶部
		    if(view.getFirstVisiblePosition() == 0){
		    }

		     break;
		   } 
	}
	@Override
	public void onRefreshStarted(View view) {
		initData(false);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
		int index = position - 1; //去掉轮播头部
		ArrayList<String> ids = new ArrayList<String>();
		
		for (StoryEntity info : news) {
			ids.add(info.getId());
		}
		Logger.e("msg", position + ":");
//		ids.remove(0);//头 时间移除
		ActivityHelper.HomeToDetail(getActivity(), ids,index);
	}
	
	@Override
	public void onSectionChange(int position) {
		//position -1  listview header
		((MainActivity)getActivity()).getSupportActionBar()
									 .setTitle(ZhihuUtils.getDateTag(getActivity(), news.get(position - 1).getDate()));
	}

}
