package com.yx.zhihu.entity;

import java.util.ArrayList;
import java.util.List;

public class ThemeSubEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String background;
	private String description;
	private ArrayList<ThemeEditerEntity> editors;
	private List<StoryEntity> stories;
	private String image;
	private String image_source;
	private String name;
	private String subscribed;
	
	public ArrayList<ThemeEditerEntity> getEditors() {
		return editors;
	}
	public void setEditors(ArrayList<ThemeEditerEntity> editors) {
		this.editors = editors;
	}
	public String getSubscribed() {
		return subscribed;
	}
	public void setSubscribed(String subscribed) {
		this.subscribed = subscribed;
	}
	public List<StoryEntity> getStories() {
		return stories;
	}
	public void setStories(List<StoryEntity> stories) {
		this.stories = stories;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ThemeSubEntity [background=" + background + ", description="
				+ description + ", editors=" + editors + ", stories=" + stories
				+ ", image=" + image + ", image_source=" + image_source
				+ ", name=" + name + ", subscribed=" + subscribed + "]";
	}
	
}
