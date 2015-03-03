package com.yx.zhihu.common;

public interface ApiConstant {
	//API接口主机
	String PHONE_SERVER = "http://news-at.zhihu.com/";
	
	String STORY_SERVER = PHONE_SERVER + "api/3/";
	
	/**
	 * 启动图
	 */
	String API_SPLASH_DATA = STORY_SERVER + "start-image/720*1184";
	
	/**
	 * MENU点击添加子标题
	 * /api/3/theme/10/subscribe	
	 */
	String API_SUBSCRIBE_ADD = STORY_SERVER + "theme/%s/subscribe";
	
	/**
	 * 
	 * /api/3/stories/latest	
	 */
	String API_HOME_STORY_LAST = STORY_SERVER + "stories/latest";
	
	/**
	 * 
	 * /api/3/stories/before/20141228
	 */
	String API_HOME_STORY_BEFORE = STORY_SERVER + "stories/before/%s";
	
	/**
	 * /api/3/themes
	 */
	String API_MENU_THEMES = STORY_SERVER + "themes";
	
	/**
	 *  api/theme/id
	 */
	String API_SUBTHEME_DATA = STORY_SERVER + "theme/%s";
	
	/**
	 *	 /api/3/story/4425797
	 */
	String API_STORY_DETAIL = STORY_SERVER + "story/%s";
	
	/**
	 *	 /api/3/story-extra/4425797
	 */
	String API_STORY_EXTRA = STORY_SERVER + "story-extra/%s";
	
	/**
	 *	 /api/3/theme/11/before/4450116  
	 */
	String API_SUBTHEME_BEFORE_DATA= STORY_SERVER + "theme/%s/before/%s";
	
	/**
	 * /api/3/editor/71/profile-page/android
	 */
	String API_EDITOR_INFO = STORY_SERVER + "editor/%s/profile-page/android";
	
	/**
	 * /api/3/story/4465567/long-comments
	 */
	String API_COMMENTS_LONG_DATA = STORY_SERVER + "story/%s/long-comments";
	
	
	/**
	 * /api/3/story/4465494/short-comments
	 */
	String API_COMMENTS_SHORT_DATA = STORY_SERVER + "story/%s/short-comments";
	
	/**
	 * /api/3/vote/story/4465494		
	 */
	String API_VOTE = STORY_SERVER + "vote/story/%s";
	
	
	int resqCode_splash = 10;
	int resqCode_lastData = 11;
	int resqCode_beforeData = 12;
	int resqCode_Themelist = 13;
	int resqCode_ThemeSubADD = 14;
	int resqCode_ThemeSubNews = 15;
	int resqCode_StoryDetail = 16;
	int resqCode_StoryEXTRA = 17;
	int resqCode_ThemeSubBefore = 18;
//	int resqCode_splash = 10;
	int resqCode_homeFocus = 1;
	
	
}
