package com.yx.zhihu.server.parser;

import java.io.InputStream;
import java.util.ArrayList;

import com.yx.zhihu.entity.BaseEntity;
import com.yx.zhihu.server.exception.ServiceException;


public abstract class BaseParser {
	
	protected final String ENCODE = "UTF-8";
	
	protected String errorCode;
	protected String date;
	
	/**
	 * Invoke parser method
	 * @author Arashmen
	 * @param <T>
	 * */
	public abstract  ArrayList<? extends BaseEntity> executeToObject(InputStream in)throws ServiceException;
	public abstract  BaseEntity executeToObject(String response)throws ServiceException;
	/**
	 * check errorcode
	 * @author Arashmen
	 * @param <T>
	 * */
	public abstract String getErrorCode();
}
