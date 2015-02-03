package com.yx.zhihu.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yx.zhihu.R;
import com.yx.zhihu.activity.MainActivity;
import com.yx.zhihu.entity.DrawerEntity;

public class DrawerMenuAdapter extends BaseLvAdapter<DrawerEntity> {
	

	public DrawerMenuAdapter(Context ct, List<DrawerEntity> lists) {
		super(ct, lists);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.fragment_navigation_drawer_list_item, null);
			holder.tv_theme = (TextView) convertView.findViewById(R.id.drawer_item_title);
			holder.iv_logo = (ImageView) convertView.findViewById(R.id.drawer_item_logo);
			holder.iv_remind = (ImageView) convertView.findViewById(R.id.drawer_item_remind);
			holder.iv_tag = (ImageView) convertView.findViewById(R.id.drawer_item_subscribe_button);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_theme.setText(lists.get(position).getName());
		if(lists.get(position).getId() == 0){
			holder.iv_logo.setVisibility(View.VISIBLE);
		}else{
			holder.iv_logo.setVisibility(View.GONE);
		}
		if(lists.get(position).isSubTheme()){
			holder.iv_tag.setBackgroundResource(R.drawable.menu_arrow);
		}else{
			holder.iv_tag.setBackgroundResource(R.drawable.menu_collect);
			holder.iv_tag.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity)ct).addSubTheme(position + 1); //首页占据第一个 所以+1;
					((MainActivity)ct).getThemelists();
				}
			});
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		public TextView tv_theme;
		public ImageView iv_logo;
		public ImageView iv_tag;
		public ImageView iv_remind;
	}

}
