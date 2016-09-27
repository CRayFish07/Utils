package com.netease.welkin.network;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class IPCheck_Thread extends Thread {

	private static String url = "http://s.weibo.com/";
	private static int CONNECT_TIMEOUT = 15 * 1000;//连接超时
	private static int  READ_TIMEOUT= 15* 1000;//读取超时
	private final PoolingHttpClientConnectionManager cm;

	public IPCheck_Thread(PoolingHttpClientConnectionManager cm){
		this.cm = cm;
	}
	@Override
	public void run() {
		IP ip = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpGet httpGet  = null;
		while((ip=IPCheck_Main.getIp())!=null){
			try {
				System.out.println(Thread.currentThread().getName()+" 开始测试"+ip.host+":"+ip.port);
				httpClient = HttpClients.custom().setConnectionManager(cm).setRoutePlanner(new DefaultProxyRoutePlanner(new HttpHost(ip.host, ip.port))).build();
				httpGet = new HttpGet(url);
				httpGet.setConfig(RequestConfig.custom().setSocketTimeout(READ_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build());
				httpGet.setHeader("Connection", "close"); 
				response = httpClient.execute(httpGet);
				int status = response.getStatusLine().getStatusCode();
				System.out.println(status);
				if(status==200){
					IPCheck_Main.saveIp(ip);
				}else {
					System.out.println(Thread.currentThread().getName()+"测试结束"+ip.host+":"+ip.port+" 为无效IP，继续提取下一个");
				}
				ip = null;
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName()+"测试结束"+ip.host+":"+ip.port+" 为无效IP，继续提取下一个");
			}finally{
				httpGet.releaseConnection();
			}
		}
		System.out.println("队列已无可用代理IP,"+Thread.currentThread().getName()+"线程结束");
	}
}
