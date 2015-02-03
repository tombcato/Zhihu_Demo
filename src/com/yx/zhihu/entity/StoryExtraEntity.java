package com.yx.zhihu.entity;

public class StoryExtraEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int comments;
	private boolean favorite;
	private int long_comments;
	private int popularity;
	private int short_comments;
	private int vote_status;
	
	
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public int getLong_comments() {
		return long_comments;
	}
	public void setLong_comments(int long_comments) {
		this.long_comments = long_comments;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public int getShort_comments() {
		return short_comments;
	}
	public void setShort_comments(int short_comments) {
		this.short_comments = short_comments;
	}
	public int getVote_status() {
		return vote_status;
	}
	public void setVote_status(int vote_status) {
		this.vote_status = vote_status;
	}
	@Override
	public String toString() {
		return "StoryExtraEntity [comments=" + comments + ", favorite="
				+ favorite + ", long_comments=" + long_comments
				+ ", popularity=" + popularity + ", short_comments="
				+ short_comments + ", vote_status=" + vote_status + "]";
	}
	
}
