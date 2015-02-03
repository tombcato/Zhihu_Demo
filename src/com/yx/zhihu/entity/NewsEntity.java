package com.yx.zhihu.entity;

import java.util.List;


public class NewsEntity extends BaseEntity {

	/**
	 * 新闻详情页
	 */
	private static final long serialVersionUID = 1L;
	
	private String body;
	private List<String> css;
	private String ga_prefix;
	private String id;
	private String image;
	private String image_source;
	private List<String> js;
	private String share_url;
	private String title;
	private int type; //0 normal ;1 topic
	
	private String editor_avatar;
	private String editor_id;
	private String editor_name;
	private String theme_id;
	private String theme_image;
	private String theme_name;
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage_source() {
		return image_source;
	}
	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}
	public List<String> getJs() {
		return js;
	}
	public void setJs(List<String> js) {
		this.js = js;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
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
	public String getEditor_avatar() {
		return editor_avatar;
	}
	public void setEditor_avatar(String editor_avatar) {
		this.editor_avatar = editor_avatar;
	}
	public String getEditor_id() {
		return editor_id;
	}
	public void setEditor_id(String editor_id) {
		this.editor_id = editor_id;
	}
	public String getEditor_name() {
		return editor_name;
	}
	public void setEditor_name(String editor_name) {
		this.editor_name = editor_name;
	}
	public String getTheme_id() {
		return theme_id;
	}
	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}
	public String getTheme_image() {
		return theme_image;
	}
	public void setTheme_image(String theme_image) {
		this.theme_image = theme_image;
	}
	public String getTheme_name() {
		return theme_name;
	}
	public void setTheme_name(String theme_name) {
		this.theme_name = theme_name;
	}
	
	
}
