package com.yx.zhihu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yx.zhihu.R;
import com.yx.zhihu.common.ZhihuUtils;
import com.yx.zhihu.entity.BeforeEntity;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.view.pinnedHeadListview.SectionedBaseAdapter;
import com.yx.zhihu.widget.news.NewsItemsWidget;

public class HomeNewsAdapter2 extends SectionedBaseAdapter{
//	private int[] sectionIndices;//用来存放每一轮分组的第一个item的位置。
//	private int[] sectionRepeatCount;
//	private String[] sectionHeaders;
	 List<Integer> sectionIndices = new ArrayList<Integer>();
	 List<Integer> sectionRepCount = new ArrayList<Integer>();
	 List<String> sectionStr = new ArrayList<String>();
	private Context ct;
	private List<StoryEntity> news;
	private LayoutInflater mInflater;
	public HomeNewsAdapter2(Context ct, List<StoryEntity> data) {
		super();
		this.ct = ct;
		this.news = data;
		mInflater = LayoutInflater.from(ct);
		getSectionIndices();
	}
	private void getSectionIndices() {
		
        if(news != null && news.size() != 0){
        	String firstDate = news.get(0).getDate();
        	String date = firstDate;
        	sectionIndices.add(0);  //上一个的坐标
        	sectionStr.add(date);	//上一个的日期
        	int j = 0;
            for (int i = 0; i < news.size(); i++) {
            	date = news.get(i).getDate();
    			if(date.equals(firstDate)){
    				j++;
    			}else{
    				sectionRepCount.add(j); //上一个循环次数
    				sectionIndices.add(i); //这一个的坐标
    				sectionStr.add(date); //这一个的日期
    				j = 1;
    				firstDate = date;
    			}
    		}
            sectionRepCount.add(j); //这一个循环次数
            
            Logger.e("msg", sectionIndices.toString() + ":" +sectionRepCount.toString() + ":" + sectionStr.toString());
        }
//        int[] sections = new int[sectionIndices.size()];
//        
//        for (int i = 0; i < sectionIndices.size(); i++) {
//            sections[i] = sectionIndices.get(i);
//        }
//        int[] reapt = new int[sectionRepCount.size()];
//        for (int i = 0; i < sectionRepCount.size(); i++) {
//			sectionRepeatCount[i] = sectionRepCount.get(i);
//		}
//        int[] str = new int[sectionRepCount.size()];
//        for (int i = 0; i < str.length; i++) {
//        	sectionHeaders[i] = sectionStr.get(i);
//		}
    }
	


	public void clear() {
        news = new ArrayList<StoryEntity>();
        sectionIndices.clear();
        sectionRepCount.clear();
        sectionStr.clear();
//        sectionIndices = new int[0];
//        sectionHeaders = new String[0];
//        sectionRepeatCount = new int[0];
        notifyDataSetChanged();
    }
	public void restore(List<StoryEntity> data) {
		news = data;
		sectionIndices.clear();
        sectionRepCount.clear();
        sectionStr.clear();
		getSectionIndices();
//		sectionIndices = getSectionIndices();//sectionStr也通过此方法更新了
        notifyDataSetChanged();
    }
	
	class HeaderViewHolder {
        TextView text;
    }

	@Override
	public Object getItem(int section, int position) {
		return null;
	}
	@Override
	public long getItemId(int section, int position) {
		return position;
	}
	@Override
	public int getSectionCount() {
		return sectionIndices.size();
	}
	@Override
	public int getCountForSection(int section) {
		return sectionRepCount.get(section);
	}
	@Override
	public View getItemView(int section, int position, View convertView,ViewGroup parent) {
		if(convertView == null){
			convertView = new NewsItemsWidget(ct);
		}
		((NewsItemsWidget)convertView).setData(news.get(position));
		return convertView;
	}
	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_home_list_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.home_list_header_date);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.text.setText(ZhihuUtils.getDateTag(ct, sectionStr.get(section)));
        return convertView;
	}
	
}
