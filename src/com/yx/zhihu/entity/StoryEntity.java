package com.yx.zhihu.entity;

import java.util.List;

public class StoryEntity extends BaseEntity {

	/**
	 * 首页新闻
	 */
	private static final long serialVersionUID = 1L;
	public static int SECTION = 0;
	public static int ITEM = 1;
	private String ga_prefix;
	private String id;
	private List<String> images;
	private boolean multipic;
	private String title;
	private int type;
	private int theme_id;
	private boolean subscribed;
	private String theme_name;
	private String share_url;
	private String image;
	
	private int TYPE = 1;
	
	
	public int getTYPE() {
		return TYPE;
	}
	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
	//日期
	private String date;
	
	
	public String getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public boolean isMultipic() {
		return multipic;
	}
	public void setMultipic(boolean multipic) {
		this.multipic = multipic;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(int theme_id) {
		this.theme_id = theme_id;
	}
	public boolean isSubscribed() {
		return subscribed;
	}
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
	public String getTheme_name() {
		return theme_name;
	}
	public void setTheme_name(String theme_name) {
		this.theme_name = theme_name;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
