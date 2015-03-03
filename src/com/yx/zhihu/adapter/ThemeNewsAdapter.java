package com.yx.zhihu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.entity.StoryEntity;
import com.yx.zhihu.entity.ThemeEditerEntity;

public class ThemeNewsAdapter extends BaseLvAdapter<StoryEntity> {
	
	private static final int TYPE_EDITOR = 0;
	private static final int TYPE_THEME_ITEM = 1;
	private List<ThemeEditerEntity> editors;
	private ListView lv;

	public ThemeNewsAdapter(Context ct, ListView lv,List<StoryEntity> lists,List<ThemeEditerEntity> editors) {
		super(ct, lists);
		this.lv = lv;
		this.editors = editors;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv_editor = null;
		View theme_editor_item = null;
		ThemeItemHolder holder = null;
		EditorsHolder edHolder = null;
//		if(theme_editor_item == null){
//			theme_editor_item = mInflater.inflate(R.layout.theme_editor_item, null);
//		}
		if(convertView == null){
			if(position == 0){
				edHolder = new EditorsHolder();
				convertView = mInflater.inflate(R.layout.fragment_theme_list_editors_item, lv,false);
				edHolder.ll_editors = (LinearLayout) convertView.findViewById(R.id.theme_editor_layout);
				convertView.setTag(edHolder);
			}else{
				holder = new ThemeItemHolder();
				convertView = mInflater.inflate(R.layout.list_item, lv,false);
				holder.tv_title = (TextView) convertView.findViewById(R.id.list_item_title);
				holder.rl_img = (RelativeLayout) convertView.findViewById(R.id.list_item_image_layout);
				holder.iv_img = (ImageView) convertView.findViewById(R.id.list_item_image);
				holder.iv_tag = (ImageView) convertView.findViewById(R.id.list_item_multipic);
				holder.ll_ext = (LinearLayout) convertView.findViewById(R.id.list_item_extra);
				convertView.setTag(holder);
			}
		}else{
			if(position == 0){
				edHolder = (EditorsHolder) convertView.getTag();
			}else{
				holder = (ThemeItemHolder) convertView.getTag();
			}
		}
		
		if(position == 0){
			//编辑头像区域
			if(editors!=null && editors.size() >0){
				edHolder.ll_editors.removeAllViews();
				for (int i = 0; i < editors.size(); i++) {
					ThemeEditerEntity info = editors.get(i);
					theme_editor_item = mInflater.inflate(R.layout.theme_editor_item, null);
					iv_editor = (ImageView) theme_editor_item.findViewById(R.id.theme_editor_avatar);
					iv_editor.setId(i+1);
					if(i>=5) {
						iv_editor.setImageResource(R.drawable.message_more);
						edHolder.ll_editors.addView(theme_editor_item);
						break;
					}
					if(!TextUtils.isEmpty(info.getUrl())){
//						bitmapUtils.display(iv_editor, info.getAvatar());
						DisplayImageOptions options = new DisplayImageOptions.Builder()  
		                .showImageOnLoading(R.drawable.account_avatar)  
		                .showImageOnFail(R.drawable.account_avatar)  
		                .cacheInMemory(true)  
		                .cacheOnDisc(true) 
		                .bitmapConfig(Bitmap.Config.RGB_565)  
		                .displayer(new RoundedBitmapDisplayer(50))
		                .build(); 
						ImageLoader.getInstance().displayImage(info.getAvatar(), iv_editor,options);
					}
					edHolder.ll_editors.addView(theme_editor_item);
				}
			}
		}else{
			//正文 主题新闻
			StoryEntity story = lists.get(position - 1);
			String image = null;
			if(story.getImages()!=null && story.getImages().size()>0){
				image =  story.getImages().get(0);
			}
			holder.tv_title.setText(story.getTitle());
			holder.ll_ext.setVisibility(View.GONE);
			if(story.isMultipic()) holder.iv_tag.setVisibility(View.VISIBLE);
			else holder.iv_tag.setVisibility(View.GONE);
			bitmapUtils.display(holder.iv_img, image);
			if(TextUtils.isEmpty(image)){
				holder.rl_img.setVisibility(View.GONE);
			}else{
				holder.rl_img.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0){
			return TYPE_EDITOR;
		}else{
			return TYPE_THEME_ITEM;
		}
	}
	
	public static class ThemeItemHolder{
		TextView tv_title;
		ImageView iv_img;
		RelativeLayout rl_img;
		ImageView iv_tag;
		LinearLayout ll_ext;
	}
	
	public static class EditorsHolder{
		LinearLayout ll_editors;
	}
	

}
