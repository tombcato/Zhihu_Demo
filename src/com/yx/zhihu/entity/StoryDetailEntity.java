package com.yx.zhihu.entity;

import java.util.List;

public class StoryDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String body;
	private List<String> css;
	private String editor_avatar;
	private String editor_id;
	private String editor_name;
	private String image;
	private String image_source;
	private String ga_prefix;
	private String id;
	private List<String> js;
	private String share_url;
	
	private String section_id;
	private String section_name;
	private String section_thumbnail;
	private String title;
	private String type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getSection_id() {
		return section_id;
	}
	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public String getSection_thumbnail() {
		return section_thumbnail;
	}
	public void setSection_thumbnail(String section_thumbnail) {
		this.section_thumbnail = section_thumbnail;
	}
	
	
	
}
