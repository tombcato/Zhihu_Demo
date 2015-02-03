package com.yx.zhihu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yx.zhihu.R;
import com.yx.zhihu.common.ZhihuUtils;
import com.yx.zhihu.entity.BeforeEntity;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.view.stickylistheaders.StickyListHeadersAdapter;
import com.yx.zhihu.widget.news.NewsItemsWidget;

public class HomeNewsAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer{
	private int[] sectionIndices;//用来存放每一轮分组的第一个item的位置。
	private String[] sectionHeaders;//用来存放每一个分组要展现的数据
	private Context ct;
	private List<StoryEntity> news;
	private LayoutInflater mInflater;
	public HomeNewsAdapter(Context ct, List<StoryEntity> data) {
		super();
		this.ct = ct;
		this.news = data;
		mInflater = LayoutInflater.from(ct);
		sectionIndices = getSectionIndices();
		sectionHeaders = getSectionLetters();
	}
	private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        String firstDate = news.get(0).getDate();
        sectionIndices.add(0);
        for (int i = 1; i < news.size(); i++) {
            if (news.get(i).getDate() != firstDate) {
                firstDate = news.get(i).getDate();
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private String[] getSectionLetters() {
        String[] dates = new String[sectionIndices.length];
        for (int i = 0; i < sectionIndices.length; i++) {
            dates[i] = ZhihuUtils.getDateTag(ct, news.get(sectionIndices[i]).getDate());
        }
        return dates;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = new NewsItemsWidget(ct);
		}
		((NewsItemsWidget)convertView).setData(news.get(position));
		return convertView;
	}
	/**
	 * getPositionForSection：
	 * 返回的是这个section数据在List<TodoTask>这个基础数据源中的位置，
	 * 因为section中的数据其实也是从List<TodoTask>中获取到的。
	 */
	@Override
	public int getPositionForSection(int section) {
	  if (sectionIndices.length == 0) {
            return 0;
        }
        
        if (section >= sectionIndices.length) {
            section = sectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return sectionIndices[section];
	}
	/**
	 * getSectionForPosition：
	 * 则是通过在基础数据源List<TodoTask>中的位置找出对应的Section中的数据，
	 * 原因同上。
	 */
	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < sectionIndices.length; i++) {
            if (position < sectionIndices[i]) {
                return i - 1;
            }
        }
        return sectionIndices.length - 1;
	}
	/**
	 * getSections：返回的其实就是Header上面要展示的数据，在这里其实就是sectionHeaders了，存放的是create_time的数据。
	 */
	@Override
	public Object[] getSections() {
		return sectionHeaders;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		 HeaderViewHolder holder;

	        if (convertView == null) {
	            holder = new HeaderViewHolder();
	            convertView = mInflater.inflate(R.layout.fragment_home_list_header, parent, false);
	            holder.text = (TextView) convertView.findViewById(R.id.home_list_header_date);
	            convertView.setTag(holder);
	        } else {
	            holder = (HeaderViewHolder) convertView.getTag();
	        }
	        holder.text.setText(ZhihuUtils.getDateTag(ct, news.get(position).getDate()));
	        return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return Long.valueOf(news.get(position).getDate());
	}


	@Override
	public int getCount() {
		if(news == null && news.size() == 0){
			return 0;
		}else{
			return news.size();
		}
	}


	@Override
	public Object getItem(int position) {
		return news.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	public void clear() {
        news = new ArrayList<StoryEntity>();
        sectionIndices = new int[0];
        sectionHeaders = new String[0];
        notifyDataSetChanged();
    }
	public void restore(List<StoryEntity> data) {
		news = data;
		sectionIndices = getSectionIndices();
		sectionHeaders = getSectionLetters();
        notifyDataSetChanged();
    }
//	public void insert(int position , T data){  
//        lists.add(position, data);  
//        this.notifyDataSetChanged();  
//	} 
	
	public void addLists(BeforeEntity data){
		
		news.addAll(data.getStories());
	}
	
	class HeaderViewHolder {
        TextView text;
    }
}
