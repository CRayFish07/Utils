package com.netease.welkin.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

public class SpiderUtil {

	/**
	 * 获取Httpresponse对象
	 * @param url 请求地址
	 * @param charset 字符集编码
	 * @return
	 */
	public static CloseableHttpResponse getHttpResponse(String url,String charset,String ip,int host) throws Exception{
		//封装代理IP
		HttpHost proxy = new HttpHost(ip, host);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);  
		CloseableHttpClient client = HttpClients.custom().setRoutePlanner(routePlanner).build();  
		//发送get请求
		HttpGet httpGet = new HttpGet(url);
		//配置参数
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();	
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("User-Agent", UserAgentUtil.getAgent());
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Host", "s.weibo.com");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
//		httpGet.setHeader("Accept-Charset", "ISO-8859-1,"+charset+";q=0.7,*;q=0.7");
		return  client.execute(proxy,httpGet);
	}

	/**
	 * @param httpResponse
	 * @param charset 字符编码
	 * @return 返回内容
	 * @throws Exception
	 */
	public static String getContent(CloseableHttpResponse httpResponse,String charset) throws Exception{
		String str = "";
		HttpEntity entity = httpResponse.getEntity();
		if(entity!=null){
			str =   EntityUtils.toString(entity,charset);
		}
		httpResponse.close();
		return str;
	}

	/**
	 * 正则匹配取出source中想要取得的部分
	 * @param regex 正则表达式
	 * @param source 要抽取的源字符串
	 * @param flag 取出数据时matcher.group(flag)中flag参数
	 * @param isFirst 标记是否取第一个匹配的
	 */
	public static String getMatchString(String regex, String source, int flag, boolean isFirst) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);

		String result = null;
		while(m.find()) {
			result = flag > 0 ? m.group(flag) : m.group();
			if(isFirst) {
				return result;
			}
		}

		return result;
	}
}