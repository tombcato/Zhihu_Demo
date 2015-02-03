package com.yx.zhihu.server.dao;

import java.util.ArrayList;

import android.net.Uri;

import com.yx.zhihu.entity.BaseEntity;


/**
 * base DAO for storing URI path & authorities
 * */
public abstract class BaseDAO {
	
	protected final String DROP_TABLE            = "DROP TABLE IF EXISTS ";	
	protected static final char CHARACTER_SLASH  = '/';
	
	/**
	 * String for VideoProvider authorities
	 * */
	public static final	String AUTHORITIES       = "BlueLightAuthorities";
	
	/**
	 * String for table name.
	 * */


	public static final String TABLE_BLUELIGHT_CP = "TABLE_BLUELIGHT_CP";
	public static final Uri URI_BLUELIGHT_CP = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_CP).toString()
													);
	
	public static final String TABLE_BLUELIGHT_FOCUS = "TABLE_BLUELIGHT_FOCUS";
	public static final Uri URI_BLUELIGHT_FOCUS = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_FOCUS).toString()
													);
	
	
	
	public static final String TABLE_BLUELIGHT_FAVORITE = "TABLE_BLUELIGHT_FAVORITE";
	public static final Uri URI_BLUELIGHT_FAVORITE = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_FAVORITE).toString()
	);
	
	public static final String TABLE_BLUELIGHT_HISTORY = "TABLE_BLUELIGHT_HISTORY";
	public static final Uri URI_BLUELIGHT_HISTORY = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_HISTORY).toString()
													);
	public static final String TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION = "TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION";
	public static final Uri URI_BLUELIGHT_MOVIE_PAY_PERMESSION = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_MOVIE_PAY_PERMESSION).toString()
													);
	public static final String TABLE_BLUELIGHT_APP_COUNT = "TABLE_BLUELIGHT_APP_COUNT";
	public static final Uri URI_BLUELIGHT_APP_COUNT = Uri.parse(new StringBuffer()
													.append("content://")
													.append(AUTHORITIES)
													.append(CHARACTER_SLASH)
													.append(TABLE_BLUELIGHT_APP_COUNT).toString()
													);
 	/**
 	 * create a String for creating table in database
 	 * @return String , String for creating table.
 	 * */
	public abstract String createTableString();
	
	/**
	 * drop table if exists
	 * @return String , for executing ,remove table from database
	 * */
	public abstract String dropTable();
	
	/**
	 * insert data into database
	 * @param obj , completely down-loaded entity info
	 * */
	public abstract void insert(ArrayList<? extends BaseEntity> arrayList);
	
	/**
	 * insert data into database
	 * @param obj , completely down-loaded entity info
	 * */
	public abstract <T extends BaseEntity> void insert(T obj);
	
	/**
	 * delete record with filter
	 * @param where , A filter to apply to rows before deleting, formatted as an SQL WHERE clause (excluding the WHERE itself).
	 * @param selectionArgs , 
	 * */
	public abstract void delete(String where ,String[] selectionArgs);
	 
	/**
	 * query records with filter
	 * @param where , A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * @param selectionArgs , You may include ?s in selection, which will be replaced by the values from selectionArgs, in the order that they appear in the selection. The values will be bound as Strings.
	 * @return ArrayList<Object> , the list of records
	 * */
	public abstract ArrayList<? extends BaseEntity> query(String[] selections,String where, String[] selectionArgs,String sortOrder);
	
	
//	public abstract <T extends BaseEntity> T query(); 
	
	/**
	 * update and reset the records
	 * @param <T>
	 * @param obj,
	 * @param where,  A filter to apply to rows before deleting, formatted as an SQL WHERE clause (excluding the WHERE itself).
	 * @param selectionArgs,
	 * */
	public abstract <T extends BaseEntity> void update(T obj,String where, String[] selectionArgs); 
	public abstract void update(ArrayList<? extends BaseEntity> arrayList);
	

}
