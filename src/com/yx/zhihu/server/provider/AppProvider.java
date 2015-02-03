package com.yx.zhihu.server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.yx.zhihu.common.AppConstant;
import com.yx.zhihu.server.dao.BaseDAO;

/**
 * data base
 * */
public class AppProvider extends ContentProvider {

	private final String DB_NAME = "hiveview_bluelight";
//	private final int DB_VERSION = 3;
	private final int DB_VERSION = 4;

	private SQLiteDatabase db;

	private UriMatcher mUriMatcher;

	/**
	 * set URI id in for created table
	 * */
	{
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_CP, AppConstant.NO_0);
		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_FOCUS, AppConstant.NO_1);
		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_FAVORITE, AppConstant.NO_2);
		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_HISTORY, AppConstant.NO_3);
		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION, AppConstant.NO_4);
		mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_BLUELIGHT_APP_COUNT, AppConstant.NO_5);
	}

	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			/* create database */
			super(context, DB_NAME, null, DB_VERSION);
		}

		/**
		 * create table
		 * */
		@Override
		public void onCreate(SQLiteDatabase db) {
//			CPDAO recordDAD = new CPDAO(getContext());
//			db.execSQL(recordDAD.createTableString());
//
//			FocusDAO focusDAO = new FocusDAO(getContext());
//			db.execSQL(focusDAO.createTableString());
//
//			FavoriteDAO favoriteDAO = new FavoriteDAO(getContext());
//			db.execSQL(favoriteDAO.createTableString());
//			
//			HistoryDAO historyDAO = new HistoryDAO(getContext());
//			db.execSQL(historyDAO.createTableString());
//			
//			PlayPermessionDAO playPermessionDAO = new PlayPermessionDAO(getContext());
//			db.execSQL(playPermessionDAO.createTableString());
//			
//			AppCountDAO appCountDAO = new AppCountDAO(getContext());
//			db.execSQL(appCountDAO.createTableString());

		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			dropAndCreate(db);

		}
		/**
		 * 以后的版本用到，哪个数据库更改字段了，
		 * 直接alterTable，就不用dropAndCreate了，这样可以保存用户原始的数据
		 */
		public void alterTable(){
//			//修改历史表
//			try {
//				//更新表结构
//				db.execSQL("alter table history add countCommend;");
//				db.execSQL("alter table history add own;");
//				db.execSQL("alter table history add is_new;");
//				db.execSQL("alter table history add isPublic;");
//				//更新数据
//				db.execSQL("update history set countCommend = '0';");
//				db.execSQL("update history set own = '0';");
//				db.execSQL("update history set is_new = '0';");
//				db.execSQL("update history set isPublic = '0';");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		private void dropAndCreate(SQLiteDatabase db) {
//			CPDAO cpDAD = new CPDAO(getContext());
//			db.execSQL(cpDAD.dropTableString());
//			db.execSQL(cpDAD.createTableString());
//			
//			FocusDAO focusDAO = new FocusDAO(getContext());
//			db.execSQL(focusDAO.dropTableString());
//			db.execSQL(focusDAO.createTableString());
//
//			FavoriteDAO favoriteDAO = new FavoriteDAO(getContext());
//			db.execSQL(favoriteDAO.dropTableString());
//			db.execSQL(favoriteDAO.createTableString());
//			
//			HistoryDAO historyDAO = new HistoryDAO(getContext());
//			db.execSQL(historyDAO.dropTableString());
//			db.execSQL(historyDAO.createTableString());
//			
//			PlayPermessionDAO playPermessionDAO = new PlayPermessionDAO(getContext());
//			db.execSQL(playPermessionDAO.dropTableString());
//			db.execSQL(playPermessionDAO.createTableString());
//			
//			AppCountDAO appCountDAO = new AppCountDAO(getContext());
//			db.execSQL(appCountDAO.dropTableString());
//			db.execSQL(appCountDAO.createTableString());
		}

	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int matchCode = mUriMatcher.match(uri);

		switch (matchCode) {
		case AppConstant.NO_0:

			db.delete(BaseDAO.TABLE_BLUELIGHT_CP, selection, selectionArgs);

			break;
		case AppConstant.NO_1:

			db.delete(BaseDAO.TABLE_BLUELIGHT_FOCUS, selection, selectionArgs);

			break;
		case AppConstant.NO_2:

			db.delete(BaseDAO.TABLE_BLUELIGHT_FAVORITE, selection, selectionArgs);

			break;
		case AppConstant.NO_3:

			db.delete(BaseDAO.TABLE_BLUELIGHT_HISTORY, selection, selectionArgs);

			break;
		case AppConstant.NO_4:
			
			db.delete(BaseDAO.TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION, selection, selectionArgs);
			break;
		case AppConstant.NO_5:
			
			db.delete(BaseDAO.TABLE_BLUELIGHT_APP_COUNT, selection, selectionArgs);
			break;
		}
		return 0;
	}

	public String getType(Uri uri) {

		return uri.toString();

	}

	public Uri insert(Uri uri, ContentValues values) {

		int matchCode = mUriMatcher.match(uri);

		switch (matchCode) {

		case AppConstant.NO_0:

			db.insert(BaseDAO.TABLE_BLUELIGHT_CP, null, values);

			break;
		case AppConstant.NO_1:

			db.insert(BaseDAO.TABLE_BLUELIGHT_FOCUS, null, values);

			break;

		case AppConstant.NO_2:

			long l = db.insert(BaseDAO.TABLE_BLUELIGHT_FAVORITE, null, values);
			break;
		case AppConstant.NO_3:

			db.insert(BaseDAO.TABLE_BLUELIGHT_HISTORY, null, values);

			break;
		case AppConstant.NO_4:
			
			db.insert(BaseDAO.TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION, null, values);
			
			break;
		case AppConstant.NO_5:
			
			db.insert(BaseDAO.TABLE_BLUELIGHT_APP_COUNT, null, values);
			
			break;
		}

		return null;
	}

	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		int matchCode = mUriMatcher.match(uri);
		Cursor cursor = null;

		switch (matchCode) {
		case AppConstant.NO_0:

			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_CP, null, selection, selectionArgs, null, null, sortOrder);

			break;
		case AppConstant.NO_1:

			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_FOCUS, null, selection, selectionArgs, null, null, sortOrder);

			break;

		case AppConstant.NO_2:

			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_FAVORITE, null, selection, selectionArgs, null, null, sortOrder);
			break;
		case AppConstant.NO_3:

			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_HISTORY, null, selection, selectionArgs,null, null, sortOrder);
			break;
		case AppConstant.NO_4:
			
			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION, null, selection, selectionArgs,null, null, sortOrder);
			break;
		case AppConstant.NO_5:
			
			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_APP_COUNT, null, selection, selectionArgs,null, null, sortOrder);
			break;
		}

		return cursor;
	}

	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		int matchCode = mUriMatcher.match(uri);
		int cursor = 0;
		switch (matchCode) {
		case AppConstant.NO_0:

//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_CP, null, selection, selectionArgs, null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_CP, values, selection, selectionArgs);

			break;
		case AppConstant.NO_1:

//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_FOCUS, null, selection, selectionArgs, null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_FOCUS, values, selection, selectionArgs);
			break;

		case AppConstant.NO_2:

//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_FAVORITE, null, selection, selectionArgs, null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_FAVORITE, values, selection, selectionArgs);
			break;
		case AppConstant.NO_3:

//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_HISTORY, null, selection, selectionArgs,null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_HISTORY, values, selection, selectionArgs);
			break;
		case AppConstant.NO_4:
			
//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_HISTORY, null, selection, selectionArgs,null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION, values, selection, selectionArgs);
			break;
		case AppConstant.NO_5:
			
//			cursor = db.query(BaseDAO.TABLE_BLUELIGHT_HISTORY, null, selection, selectionArgs,null, null, sortOrder);
			cursor = db.update(BaseDAO.TABLE_BLUELIGHT_APP_COUNT, values, selection, selectionArgs);
			break;
		}

		return 0;
	}
	
	@Override
	public boolean onCreate() {

		db = new DBHelper(getContext()).getWritableDatabase();
		return false;
	}
}
