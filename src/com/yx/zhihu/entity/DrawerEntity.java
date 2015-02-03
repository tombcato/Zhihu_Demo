package com.yx.zhihu.entity;

public class DrawerEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String color;
	private String description;
	private int id;
	private String image;
	private String name;
	
	private boolean isSubTheme = false;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSubTheme() {
		return isSubTheme;
	}
	public void setSubTheme(boolean isSubTheme) {
		this.isSubTheme = isSubTheme;
	}
	
	
	
}
