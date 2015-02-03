package com.yx.zhihu.entity;

import java.util.List;

public class HomeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<StoryEntity> topNews;
	private List<BeforeEntity> news;
	
	public List<StoryEntity> getTopNews() {
		return topNews;
	}
	public void setTopNews(List<StoryEntity> topNews) {
		this.topNews = topNews;
	}
	public List<BeforeEntity> getNews() {
		return news;
	}
	public void setNews(List<BeforeEntity> news) {
		this.news = news;
	}
	
	public HomeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HomeEntity(List<StoryEntity> topNews, List<BeforeEntity> news) {
		super();
		this.topNews = topNews;
		this.news = news;
	}
	
}
