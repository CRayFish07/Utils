package com.netease.welkin.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemThreadUtil {

	
	public static Thread containThread(String threadName) {

		Thread[] threads = findAllThreads();
		
		for (Thread thread : threads) {
			if (thread.getName().equals(threadName)) {
				return thread;
			}
		}
		return null;
	}
	
	
	
	public static String getLocalIP(){
		
		InetAddress addr = null ;
	    try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip = addr.getHostAddress();
		return ip;
	}
	
	/**
	 *  获取Java VM中当前运行的所有线程
	 * @return
	 */
	public static Thread[] findAllThreads() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
	    // 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slacks = new Thread[estimatedSize];
	     //获取根线程组的所有线程
		int actualSize = topGroup.enumerate(slacks);
		Thread[] threads = new Thread[actualSize];
		System.arraycopy(slacks, 0, threads, 0, actualSize);
		return threads;
	}
	
	
	
}
