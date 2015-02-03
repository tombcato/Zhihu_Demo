package com.yx.zhihu.adapter;

import java.util.List;

import com.yx.zhihu.view.customviewpager.ViewPagerCompat;
import com.yx.zhihu.widget.news.NewsDetialWidget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class NewsDetailAdapter extends PagerAdapter {

	private List<NewsDetialWidget> news;
	private Context ct;

	public NewsDetailAdapter(Context ct,List<NewsDetialWidget> news) {
		this.ct = ct;
		this.news = news;
	}
	@Override
	public int getCount() {
		return news.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPagerCompat) container).removeView((NewsDetialWidget)object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPagerCompat) container).addView(news.get(position), 0);
		return news.get(position);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1 ;
	}
	
	@Override
	public int getItemPosition(Object object) {
        return POSITION_NONE;    
	}

}
