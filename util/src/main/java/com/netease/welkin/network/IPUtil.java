package com.netease.welkin.network;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.LinkedBlockingQueue;

public class IPUtil {

	public static LinkedBlockingQueue<IP> queue = new LinkedBlockingQueue<IP>();

	public static void initIps(){
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File("conf/proxyip_ok")),"UTF-8");//考虑到编码格式		
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt = bufferedReader.readLine()) != null){
				queue .put(new IP(lineTxt.substring(0,lineTxt.indexOf(":")), Integer.parseInt(lineTxt.substring(lineTxt.indexOf(":")+1).trim())));
			}
			System.out.println("IP队列的长度为:"+queue.size());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 杰出队列头部ip
	 * @return
	 */
	public synchronized static IP borrowIP() {
		IP ip = queue.poll();
		while (ip == null) {
			try {
				Thread.sleep(5);// 等等归还
			} catch (Exception e) {
				e.printStackTrace();
			}
			ip = queue.poll();
			if (ip == null) {
				System.out.println("ip是空");
			}
		}
		return ip;
	}

	/**
	 * 归还ip,放到队列末尾
	 * @param ip
	 */
	public static void returnIP(IP ip) {
		try {
			queue .put(ip);
			System.out.println("归还一个代理IP，当然代理队列中共剩: "+queue.size()+" 个代理IP");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
