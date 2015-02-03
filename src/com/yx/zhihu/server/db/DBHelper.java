package com.yx.zhihu.server.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DBHelper extends SQLiteOpenHelper {
	
	//db
	public static final String DB_NAME = "news_paper.db";
	public static final int DB_VERSION = 7;
	
	//1.news_list 
	//store news list and news detail
	public static final String NEWS_TABLE_NAME = "news_list";
	public static final String NEWS_COLUMN_ID = "_id";
	public static final String NEWS_COLUMN_TYPE = "type";
	public static final String NEWS_COLUMN_KEY = "key";
	public static final String NEWS_COLUMN_CONTENT = "content";
	
	private static final String NEWS_TABLE_CREATE = "CREATE TABLE " + NEWS_TABLE_NAME
			+ "(" + NEWS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NEWS_COLUMN_TYPE + " INTEGER NOT NULL, "
			+ NEWS_COLUMN_KEY + " CHAR(256) UNIQUE NOT NULL, " + NEWS_COLUMN_CONTENT
			+ " TEXT NOT NULL);";

	//2.news_read
	public static final String READ_TABLE_NAME = "news_read";
	public static final String READ_COLUMN_ID = "_id";
	public static final String READ_COLUMN_NEWSID = "news_id";
	
	private static final String READ_TABLE_CREATE = "CREATE TABLE " + READ_TABLE_NAME
			+ "(" + READ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ READ_COLUMN_NEWSID + " CHAR(256) UNIQUE);";
	
	//3.news_favorite
	public static final String FAVORITE_TABLE_NAME = "news_favorite";
	public static final String FAVORITE_COLUMN_ID = "_id";
	public static final String FAVORITE_COLUMN_NEWS_ID = "news_id";
	public static final String FAVORITE_COLUMN_NEWS_TITLE = "news_title";
	public static final String FAVORITE_COLUMN_NEWS_LOGO = "news_logo";
	public static final String FAVORITE_COLUMN_NEWS_SHARE_URL = "news_share_url";
	
	private static final String FAVORITE_TABLE_CREATE = "CREATE TABLE " + FAVORITE_TABLE_NAME
			+ "(" + FAVORITE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FAVORITE_COLUMN_NEWS_ID + " CHAR(256) UNIQUE, "
			+ FAVORITE_COLUMN_NEWS_TITLE + " CHAR(1024), "
			+ FAVORITE_COLUMN_NEWS_LOGO + " CHAR(1024), "
			+ FAVORITE_COLUMN_NEWS_SHARE_URL + " CHAR(1024));";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(NEWS_TABLE_CREATE);
		db.execSQL(READ_TABLE_CREATE);
		db.execSQL(FAVORITE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + READ_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE_NAME);
		
		onCreate(db);
	}
}