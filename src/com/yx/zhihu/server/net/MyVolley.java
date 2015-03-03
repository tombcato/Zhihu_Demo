package com.yx.zhihu.server.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yx.zhihu.APP;
import com.yx.zhihu.utils.Logger;
import com.yx.zhihu.utils.Network;

@SuppressWarnings("unused")
public class MyVolley {
	protected static final String TAG = MyVolley.class.getSimpleName();
	private final int RESQCODE_DEFALUT = 0;
	private RequestQueue queue;
	private Context mContext;
	private VolleyCallBack mCallBack;
	private Gson gson = new Gson();
	private String errorCode;
	private APP app;

	public MyVolley(Context ctx, VolleyCallBack callback) {
		this.mContext = ctx;
		this.mCallBack = callback;
		app = APP.getApp();
		queue = app.getVolleyQueue();
	}

	/** 
	 * @param resquestCode 请求码，唯一标识请求作用，可精准回调用
	 * @param url 请求地址
	 * @param cls JavaBean
	 *            通用的volley get请求 统一Response为String 通过GSON 解析成类或者lsit
	 *            所用的entity里每个属性需要按照后台接口提供的数据来创建
	 */
//	@SuppressWarnings("unused")
//	public <T> void volleyGet(final int requestCode,String url, final Class<T> cls) {
//		
//		Logger.e(TAG, "request: " + url);
//		
//		StringRequest getRequest = new StringRequest(url,new Response.Listener<String>() {
//			
//					@Override
//					public void onResponse(String response) {
//						T entity = null ;
//						try {
////							apiResult = ParserResult(requestCode, cls, response);
////							mCallBack.VolleyloadDone(resquestCode, parserResult,errorCode);
//							Logger.e(TAG, "response: " + response );
//							if(!TextUtils.isEmpty(response) && cls != null){
//								entity = gson.fromJson(response.toString(),(Class<T>) cls);
//							}
//						} catch (Exception e) {
//							Logger.e(TAG, e.getMessage());
////							apiResult.exception = e.getMessage();
//						}
////						mCallBack.VolleyloadDone(apiResult);
//						mCallBack.VolleyloadDone(requestCode, entity);
//					}
//
//				}, new Response.ErrorListener() {
//					
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						Logger.e(TAG, error.getMessage());
////						mCallBack.VolleyloadDone(new ApiResult<T>(error.getMessage()));
//					}
//				});
//		getRequest.setShouldCache(true);
////		getRequest.setCacheEntry(entry);
//		getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//		queue.add(getRequest);
//	}
	public <T> void volleyGet(final int requestCode,String url, final Class<T> cls,final boolean isCacheRequest) {
		Logger.e(TAG, "request: " + url);
		JsonObjectRequest getRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				T entity = null ;
				try {
//					apiResult = ParserResult(requestCode, cls, response);
//					mCallBack.VolleyloadDone(resquestCode, parserResult,errorCode);
					Logger.e(TAG, "response: " + response );
					if(response!=null && cls != null){
						entity = gson.fromJson(response.toString(),(Class<T>) cls);
					}
				} catch (Exception e) {
					Logger.e(TAG, e.getMessage());
//					apiResult.exception = e.getMessage();
				}
//				mCallBack.VolleyloadDone(apiResult);
				mCallBack.VolleyloadDone(requestCode, entity);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Logger.e(TAG, error.getMessage());
				mCallBack.VolleyloadDone(requestCode, null);
			}
		}){
			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
		            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		            return Response.success(new JSONObject(jsonString),CustomHttpHeaderParser.parseCacheHeaders(response,1000*60*60*24*7));
		        } catch (UnsupportedEncodingException e) {
		            return Response.error(new ParseError(e));
		        } catch (JSONException je) {
		            return Response.error(new ParseError(je));
		        }
			}
		};
//		getRequest.set
		getRequest.setShouldCache(isCacheRequest);
		getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(getRequest);
//		queue.addWithDontRequestCache(getRequest);
	}	
	

	/**
	 * volley post方法
	 * @param requestCode 请求码，唯一标识请求作用，可精准回调用
	 * @param url 请求地址
	 * @param cls JavaBean
	 * @param map 
	 * 
	 */
	@SuppressWarnings("unused")
	public <T> void volleyPost(final int requestCode,String url, final Class<T> cls,
			final HashMap<String, String> map) {
		
		StringRequest postRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						ApiResult<T> apiResult = new ApiResult<T>();
						try {
							apiResult = ParserResult(requestCode, cls, response);
//							mCallBack.VolleyloadDone(requestCode, list, errorCode);
						} catch (JSONException e) {
							Logger.e(TAG, e.getMessage());
							apiResult.exception = e.getMessage();
						}
//						mCallBack.VolleyloadDone(apiResult);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return map;
			}

			@Override
			protected Response<String> parseNetworkResponse(NetworkResponse response) {
				 String parsed;
			        try {
			            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			        } catch (UnsupportedEncodingException e) {
			            parsed = new String(response.data);
			        }
			     return Response.success(parsed, CustomHttpHeaderParser.parseCacheHeaders(response,1000*60*60*24));
			}

		};
		
		postRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(postRequest);
	}
	
	private <T> ApiResult<T> ParserResult(final int resquestCode,final Class<T> cls, String response)
			throws JSONException {
		Object json;
		List<T> list = new ArrayList<T>();
		JSONObject jo1 = new JSONObject(response);
		String errorCode = jo1.getString("code");
		if (cls != null) {
			json = new JSONTokener(jo1.getString("data")).nextValue();
			if (json instanceof JSONObject) {
				JSONObject obj = (JSONObject) json;
				T entity = gson.fromJson(obj.toString(),(Class<T>) cls);
				list.add(entity);
			} else if (json instanceof JSONArray) {
				JSONArray ja = (JSONArray) json;
				for (int i = 0; i < ja.length(); i++) {
					list.add(gson.fromJson(ja.get(i).toString(), cls));
				}
			}
		}
		return new ApiResult<T>(errorCode,resquestCode,list);
		
	}

	public interface VolleyCallBack {
//		public <T> void VolleyloadDone(int resquestCode,List<T> list,String errorCode);
//		public <T> void VolleyloadDone(ApiResult<T> apiResult);
		public <T> void VolleyloadDone(int resqCode,T entity);
	}
	
	public class ApiResult<T>{
		
		public String errorCode;
		public int resquestCode;
		public List<T> dataList;
		public String exception;
		
		public ApiResult() {
			super();
		}

		public ApiResult(String errorCode, int resquestCode, List<T> dataList) {
			super();
			this.errorCode = errorCode;
			this.resquestCode = resquestCode;
			this.dataList = dataList;
		}

		public ApiResult(String errorCode, int resquestCode, List<T> dataList,String exception) {
			super();
			this.errorCode = errorCode;
			this.resquestCode = resquestCode;
			this.dataList = dataList;
			this.exception = exception;
		}

		public ApiResult(String exception) {
			super();
			this.exception = exception;
		}
		
		
	}
	
	/**
	 * 自定义的HeaderParser,跟默认的比，可以强制缓存，忽略服务器的设置
	 */
	public static class CustomHttpHeaderParser extends HttpHeaderParser {
	    /**
	     * Extracts a {@link com.android.volley.Cache.Entry} from a {@link com.android.volley.NetworkResponse}.
	     *
	     * @param response The network response to parse headers from
	     * @param cacheTime 缓存时间，如果设置了这个值，不管服务器返回是否可以缓存，都会缓存,一天为1000*60*60*24
	     * @return a cache entry for the given response, or null if the response is not cacheable.
	     */
	    public static Cache.Entry parseCacheHeaders(NetworkResponse response,long cacheTime) {
	        Cache.Entry entry=parseCacheHeaders(response);
	        long now = System.currentTimeMillis();
	        long softExpire=now+cacheTime;
	        entry.softTtl = softExpire;
	        entry.ttl = entry.softTtl;
	        return entry;
	    }
	}
	
}
