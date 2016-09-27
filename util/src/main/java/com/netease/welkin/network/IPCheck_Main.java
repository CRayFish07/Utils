package com.netease.welkin.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class IPCheck_Main {
	private static Queue<IP> queue  = new LinkedList<IP>();
	private static Queue<IP> queueOK  = new LinkedList<IP>();

	/*
	 * ht   代理类型 0全部，1http，2https
	 * yys 运营商     0全部，1电信，2联通(网通)，3移动，4铁通
	 * amt 匿名性质 3 高级匿名 2普通匿名 1 透明代理 
	 * 
	 * 
	 */
	String IPAPI = "http://1.iiipt.com/getIp/getApi.ashx?oid=C697F428D5C44BC08008A49852A5BB95&num=999&ht=2&yys=1&amt=3,2&sp=1&cy=中国&port=80,8088&gv=1";
	/**
	 * 初始化获取ip
	 */
	public static void init(){
		String lineTxt = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/crawler/sunshinan/project/check_ip/conf/proxyip.txt")));
			while((lineTxt = br.readLine()) != null){
				String host = lineTxt.substring(0,lineTxt.indexOf(":"));
				int port = 0;
				if(lineTxt.contains("@")){
					port = Integer.parseInt(lineTxt.substring(lineTxt.indexOf(":")+1,lineTxt.indexOf("@")));
				}else {
					port = Integer.parseInt(lineTxt.substring(lineTxt.indexOf(":")+1));
				}
				IP ip = new IP(host, port);
				queue.add(ip);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从初始队列中 取出代理IP
	 * @return
	 */
	public synchronized static IP getIp(){
		if(queue!=null&&queue.size()!=0){
			System.out.println(Thread.currentThread().getName()+"~~从队列中取出一个代理IP，还剩下"+queue.size()+"个");
			return queue.poll();
		}else {
			System.out.println(Thread.currentThread().getName()+"~~从队列中取出不到代理IP，还剩下"+queue.size()+"个");
			return null;
		}
	}

	/**
	 * 将可用代理IP 填入队列
	 * @param ip
	 */
	public synchronized static void saveIp(IP ip){
		queueOK.add(ip);
		System.err.println(Thread.currentThread(). getName()+"***向成功队列中添加一条IP，共计"+queueOK.size()+"个可用代理IP***");
	}

	/**
	 * gogogo
	 */
	public static void test(){
		int count =20;
		
		System.out.println("***开始向队列录入预备代理IP***");
		init();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);

		IPCheck_Thread[] threadTests = new IPCheck_Thread[count];
		System.out.println("***录入完成，共计"+queue.size()+"个预备代理IP***");

		System.out.println("***开始创建线程***");
		for (int i = 0; i < threadTests.length; i++) {
			threadTests[i] = new IPCheck_Thread(cm);
			threadTests[i].setName("Thread-"+(i+1));
		}
		System.out.println("***线程创建成功***");

		System.out.println("***线程开始运行***");
		for (int i = 0; i < threadTests.length; i++) {
			threadTests[i].start();
		}

		for (int i = 0; i < threadTests.length; i++) {
			try {
				threadTests[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.err.println("采集完成，可用的代理IP一共"+queueOK.size()+"个");
	}

	public static void insertIP(){
		try {
			FileWriter fileWriter = new FileWriter("/home/crawler/sunshinan/project/check_ip/conf/proxyip_ok.txt");
			BufferedWriter bw = new BufferedWriter(fileWriter);
			IP ip = null;
			while((ip=queueOK.poll())!=null){
				bw.append(ip.host+":"+ip.port+"\n");
			}
			bw.flush();
			bw.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test();
		insertIP();
	}
}
