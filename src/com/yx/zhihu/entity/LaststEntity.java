package com.yx.zhihu.entity;

import java.util.List;

public class LaststEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private List<StoryEntity> stories;
	private List<StoryEntity> top_stories;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<StoryEntity> getStories() {
		return stories;
	}
	public void setStories(List<StoryEntity> stories) {
		this.stories = stories;
	}
	public List<StoryEntity> getTop_stories() {
		return top_stories;
	}
	public void setTop_stories(List<StoryEntity> top_stories) {
		this.top_stories = top_stories;
	}
	
	
}
