package com.yx.zhihu.entity;

import java.util.List;

public class ThemeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit;
	private List<DrawerEntity> others;
	private List<DrawerEntity> subscribed;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List<DrawerEntity> getOthers() {
		return others;
	}
	public void setOthers(List<DrawerEntity> others) {
		this.others = others;
	}
	public List<DrawerEntity> getSubscribed() {
		return subscribed;
	}
	public void setSubscribed(List<DrawerEntity> subscribed) {
		this.subscribed = subscribed;
	}
	
	
}
