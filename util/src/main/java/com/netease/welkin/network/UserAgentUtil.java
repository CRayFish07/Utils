package com.netease.welkin.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAgentUtil {

	private static List<String> agents = new ArrayList<String>();
	private static  Random random = new Random();

	static{
		agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.35 (KHTML, like Gecko) Chrome/27.0.1448.0 Safari/537.35");//chrome
		agents.add("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.10) Gecko/20100914 Firefox/3.6.10");//火狐
		agents.add("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0))");//IE
		agents.add("Opera/9.27 (Windows NT 5.2; U; zh-cn)");//Opera
		agents.add(" Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 Safari/525.13");//Safari
		agents.add("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080219 Firefox/2.0.0.12 Navigator/9.0.0.6");//Navigator
		agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");//Sogou
		agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");//Sogou
		agents.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; 360se)");//360
		agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17 QIHU 360EE");//360极速
		agents.add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; TencentTraveler 4.0; .NET CLR 2.0.50727))");//腾讯TT
	}
	public static String getAgent(){
		int index = random.nextInt(agents.size());
		return agents.get(index);
	}
}
