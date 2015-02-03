package com.yx.zhihu.server.net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpTaskManager {
	/**
	 * Java通过Executors提供四种线程池，分别为：
		newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
		newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
		newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	 */
	private static final int SIZE = 5;
	
	private static ExecutorService POOL = Executors.newFixedThreadPool(SIZE);
	private static HttpTaskManager MANAGER = new HttpTaskManager();
	
	public static HttpTaskManager getInstance(){
		
		if(MANAGER.isShutdown()){
			POOL = null;
			POOL = Executors.newFixedThreadPool(SIZE);
		}
		
		return MANAGER;
	}
	
	public void submit(Runnable obj){
		
		POOL.submit(obj);
	}
	
	public void submit(Callable<?> obj){
		
		POOL.submit(obj);
		
	}
	
	public void shutdown(){
		
		POOL.shutdown();
		
	}
	
	public boolean isShutdown(){
		
		return POOL.isShutdown();
		
	}
	
	public boolean isTerminated(){
		
		return POOL.isTerminated();
		
	}
	
	public boolean awaitTermination(long timeOut,TimeUnit unit) throws InterruptedException{
		
		return POOL.awaitTermination(timeOut, unit);
		
	}
	
//	public void shutDownNow(){
//		POOL.shutdownNow();
//		
//		System.out.println("isTerminated: " + isTerminated());
//		System.out.println("isShutdown: " + isShutdown());
//		
//		getInstance();
//	}
	
}
