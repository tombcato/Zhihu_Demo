package com.yx.zhihu.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ThemeEditerEntity extends BaseEntity implements Parcelable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String avatar;
	private String bio;
	private int id;
	private String name;
	private String url;
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ThemeEditerEntity [avatar=" + avatar + ", bio=" + bio + ", id="
				+ id + ", name=" + name + ", url=" + url + "]";
	}
	
	public ThemeEditerEntity(String avatar, String bio, int id, String name,
			String url) {
		super();
		this.avatar = avatar;
		this.bio = bio;
		this.id = id;
		this.name = name;
		this.url = url;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(avatar);
		dest.writeString(bio);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(url);
	}
	
	public static final Parcelable.Creator<ThemeEditerEntity> CREATOR = new Parcelable.Creator<ThemeEditerEntity>() {

		@Override
		public ThemeEditerEntity createFromParcel(Parcel source) {
			return new ThemeEditerEntity(source.readString(),
										 source.readString(),
										 source.readInt(),
										 source.readString(),
										 source.readString());
		}

		@Override
		public ThemeEditerEntity[] newArray(int size) {
			return new ThemeEditerEntity[size];
		}
	};
	
	
}
