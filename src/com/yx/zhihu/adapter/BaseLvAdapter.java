package com.yx.zhihu.adapter;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.yx.zhihu.APP;
import com.yx.zhihu.utils.BitmapUtil;
import com.yx.zhihu.utils.ResolutionUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseLvAdapter<T> extends BaseAdapter {
	
	protected Context ct;
	protected List<T> lists;
	protected LayoutInflater mInflater;
	protected ResolutionUtil resolution;
	protected BitmapUtils bitmapUtils;
	
	public BaseLvAdapter() {
		super();
	}

	public BaseLvAdapter(Context ct, List<T> lists) {
		super();
		this.ct = ct;
		this.lists = lists;
		init();
	}
	
	public void changeAdapterData(List<T> lists){
		this.lists = lists;
		init();
	}

	private void init() {
		mInflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resolution = APP.getApp().getResolution();
		bitmapUtils = new BitmapUtils(ct);
	}

	@Override
	public int getCount() {
		if(lists == null) 
			return 0;
		else 
			return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

//	@Override
//	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	public void insert(int position , T data){  
        lists.add(position, data);  
        this.notifyDataSetChanged();  
	} 
	
	public void removeItem(int position){  
        if(lists.size()<=0)return;  
        if(position<0)return;  
        if(position>lists.size()-1)return;  
          
        lists.remove(position);  
        this.notifyDataSetChanged();  
    }  

}
 
	