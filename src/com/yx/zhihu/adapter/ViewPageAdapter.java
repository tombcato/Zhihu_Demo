package com.yx.zhihu.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPageAdapter extends PagerAdapter{

	private List<View> mViewList;
	private Integer curUpdatePager;
	
	public ViewPageAdapter(List<View> viewList){
		this.mViewList = viewList;
	}
	
	public void clearAllData(){
		this.mViewList.clear();
		this.notifyDataSetChanged();
	}
	
	public void changeList(List<View> viewList){
		this.mViewList = viewList;
	}
	
	@Override
	public int getCount() {
		if(mViewList != null){
			return mViewList.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View view, int index) {
		((ViewPager) view).addView(mViewList.get(index), 0);
		return mViewList.get(index);
	}
	
	@Override
	public void destroyItem(View view, int position, Object object) {
		((ViewPager)view).removeView((View)object);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public int getItemPosition(Object object) {
        return POSITION_NONE;    
	}
	
}
