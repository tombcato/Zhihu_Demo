package com.yx.zhihu.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yx.zhihu.R;
import com.yx.zhihu.common.ZhihuUtils;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.view.PinnedSectionListView.PinnedSectionListAdapter;
import com.yx.zhihu.widget.news.NewsItemsWidget;

public class HomeNewsAdapter3 extends BaseLvAdapter<StoryEntity> implements PinnedSectionListAdapter{
	
	public HomeNewsAdapter3() {
		super();
	}
	public HomeNewsAdapter3(Context ct, List<StoryEntity> lists) {
		super(ct, lists);
	}
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	@Override
	public boolean isEnabled(int position) {
		if(lists.get(position).getTYPE() == StoryEntity.SECTION){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public int getItemViewType(int position) {
		return lists.get(position).getTYPE();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		if(convertView == null){
			if(getItemViewType(position) == StoryEntity.ITEM){
				convertView = new NewsItemsWidget(ct);
				convertView.setLeft(resolution.px2dp2px(20, true));
				convertView.setTop(resolution.px2dp2px(10, false));
				convertView.setBottom(resolution.px2dp2px(10, false));
				convertView.setRight(resolution.px2dp2px(20, true));
			}else if(getItemViewType(position) == StoryEntity.SECTION){
				 holder = new HeaderViewHolder();
		            convertView = mInflater.inflate(R.layout.fragment_home_list_header, parent, false);
		            holder.text = (TextView) convertView.findViewById(R.id.home_list_header_date);
		            convertView.setTag(holder);
			}
		}else{
			if(getItemViewType(position) == StoryEntity.ITEM){
//				(NewsItemsWidget)convertView;
			}else if(getItemViewType(position) == StoryEntity.SECTION){
				holder = (HeaderViewHolder) convertView.getTag();
			}
		}
		
		if(getItemViewType(position) == StoryEntity.ITEM){
			((NewsItemsWidget)convertView).setData(lists.get(position));
		}else if(getItemViewType(position) == StoryEntity.SECTION){
			 holder.text.setText(ZhihuUtils.getDateTag(ct, lists.get(position).getDate()));
		}
		return convertView;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == StoryEntity.SECTION;
	}
	
	static class HeaderViewHolder {
        TextView text;
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
	
}
