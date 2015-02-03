package com.yx.zhihu.server.exception;

/**
 * 接口异常
 * 
 * @author HiveView  - Arashmen
 *
 */
public class ServiceException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String detailMessage;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public ServiceException(String detailMessage) {
		super(detailMessage);
		this.detailMessage = detailMessage;
	}
	
	public ServiceException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}
	 
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	public String getDetailMessage() {
		return detailMessage;
	}
	
}
