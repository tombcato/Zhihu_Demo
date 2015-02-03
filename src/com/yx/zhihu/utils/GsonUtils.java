package com.yx.zhihu.utils;

import java.util.ArrayList;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yx.zhihu.entity.BaseEntity;
import com.yx.zhihu.entity.BeforeEntity;
import com.yx.zhihu.entity.NewsEntity;

public class GsonUtils {

//	public static ArrayList<NewsEntity> getNewsList(String content) {
//
//		if (TextUtils.isEmpty(content))
//			return null;
//
//		Gson gson = new Gson();
//
//		try {
//			BeforeEntity newsListEntity = gson.fromJson(content,BeforeEntity.class);
//			return newsListEntity != null ? newsListEntity.getStories() : null;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	/**
	 * 解析一个字符串，得到BaseEntity对象
	 * 
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static BaseEntity getEntity(String content, Class<?> clazz) {

		if (TextUtils.isEmpty(content))
			return null;

		Gson gson = new Gson();
		
		try {
			BaseEntity baseEntity = (BaseEntity) gson.fromJson(content, clazz);
			return baseEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}