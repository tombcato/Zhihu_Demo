package com.yx.zhihu.adapter;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.entity.ThemeEditerEntity;

public class EditorsShowAdapter extends BaseLvAdapter<ThemeEditerEntity> {
	

	private ListView lv;

	public EditorsShowAdapter(Context ct, ListView lv,List<ThemeEditerEntity> lists) {
		super(ct, lists);
		this.lv = lv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(ct);
			convertView = inflater.inflate(R.layout.activity_editors_list_item, lv,false);
			holder.iv_editor_photo = (ImageView) convertView.findViewById(R.id.theme_editor_image);
			holder.tv_editor_name = (TextView) convertView.findViewById(R.id.theme_editor_name);
			holder.tv_editor_bio = (TextView) convertView.findViewById(R.id.theme_editor_bio);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_editor_name.setText(lists.get(position).getName());
		holder.tv_editor_bio.setText(lists.get(position).getBio());
		ImageLoader.getInstance().displayImage(lists.get(position).getAvatar(), holder.iv_editor_photo, APP.getApp().getDefalutOptions());
		return convertView;
	}
	
	static class ViewHolder{
		ImageView iv_editor_photo;
		TextView tv_editor_name;
		TextView tv_editor_bio;
	}
}
