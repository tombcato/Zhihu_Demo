package com.yx.zhihu.entity;

import java.util.List;

public class BeforeEntity extends BaseEntity {

	/**
	 * 过往的数据
	 */
	private static final long serialVersionUID = 1L;
	
	private String date;
	private List<StoryEntity> stories;
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
	
	public BeforeEntity() {
		super();
	}
	public BeforeEntity(String date, List<StoryEntity> stories) {
		super();
		this.date = date;
		this.stories = stories;
	}
	
	
}
