package com.yx.zhihu.widget.news;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.activity.MainActivity;
import com.yx.zhihu.entity.StoryEntity;

public class NewsItemsWidget extends RelativeLayout implements OnClickListener {

	private View view;
	private TextView tv_title;
	private ImageView iv_img;
	private ImageView iv_mult_tag;
	private TextView tv_extra_text;
	private TextView tv_extra_from;
	private LinearLayout ll_extra_layout;
	private RelativeLayout rl_img_layout;
	private int theme_id;


	public NewsItemsWidget(Context context) {
		super(context);
		init();
	}

	public NewsItemsWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NewsItemsWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	

	private void init() {
		LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.list_item, null);
		
		addView(view);
		initView();
	}

	private void initView() {
		tv_title = (TextView) view.findViewById(R.id.list_item_title);
		TextView tv_intro = (TextView) view.findViewById(R.id.list_item_intro);
		rl_img_layout = (RelativeLayout) view.findViewById(R.id.list_item_image_layout);
		iv_img = (ImageView) view.findViewById(R.id.list_item_image);
		iv_mult_tag = (ImageView) view.findViewById(R.id.list_item_multipic);
		
		ll_extra_layout = (LinearLayout) view.findViewById(R.id.list_item_extra);
		tv_extra_from = (TextView) view.findViewById(R.id.list_item_extra_text_choose_from);
		tv_extra_text = (TextView) view.findViewById(R.id.list_item_extra_text);
		
		ll_extra_layout.setOnClickListener(this);
	}
	
	public void setData(StoryEntity info){
		
		tv_title.setText(info.getTitle());
		
		if(info.getImages()!= null && info.getImages().size() > 0){
			APP.getApp().getBitmapUtils().configDefaultLoadingImage(R.drawable.ic_logo)
										 .display(iv_img, info.getImages().get(0));
//			ImageLoader.getInstance().displayImage(info.getImages().get(0), iv_img, APP.getApp().getDefalutOptions());
			rl_img_layout.setVisibility(View.VISIBLE);
		}else{
			rl_img_layout.setVisibility(View.GONE);
		}
		
		if(!TextUtils.isEmpty(info.getTheme_name())){
			ll_extra_layout.setVisibility(View.VISIBLE);
			tv_extra_from.setText(info.getTheme_name());
			tv_extra_text.setText(info.getTheme_name());
			theme_id = info.getTheme_id();
		}else{
			ll_extra_layout.setVisibility(View.GONE);
		}
		
		if(info.isMultipic()){
			iv_mult_tag.setVisibility(View.VISIBLE);
		}else{
			iv_mult_tag.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View arg0) {
		((MainActivity)getContext()).toSubNews(theme_id);
	}

	

}
