package com.netease.welkin.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;

/**
 * ClassName:Crawler4all Description:
 * 
 * @author zengxiangdong
 * @Date 2012 2012-6-26 上午11:00:33
 */
public class CrawlerUtils {

    private static Logger logger = LoggerFactory.getLogger(CrawlerUtils.class);

	// 生成随机数用于sleep
	static List<Long> randomList = null;
	public static JSONObject json = null;

	private static Proxy proxy;

	static {
		randomList = getRandomList();
	}

	/**
	 * 生成随机数用于sleep
	 * 
	 * @return 设定文件
	 */
	private static List<Long> getRandomList() {
		if(randomList == null) {
			randomList = new ArrayList<Long>();

			for(int i = 2001; i <= 5000; i++) {
				long num = (long)(i + Math.random() * 3000);
				randomList.add(num);
			}
		}

		return randomList;
	}

	/**
	 * 取得HtmlUnitDriver driver
	 */
	public static HtmlUnitDriver getDriver() {
		IminerHtmlUnitDriver driver = new IminerHtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

		driver.setJavascriptEnabled(false);

		return driver;
	}

	/**
	 * 取得HtmlUnitDriver driver
	 */
	public static HtmlUnitDriver getDriverWithProxy() {

		IminerHtmlUnitDriver driver = new IminerHtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

		driver.setJavascriptEnabled(false);

		return driver;
	}

	/**
	 * 取得HtmlUnitDriver driver，登陆后的driver
	 */
	public static HtmlUnitDriver getDriverByLogin() {

		IminerHtmlUnitDriver driver = new IminerHtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

		driver.setJavascriptEnabled(false);

		return driver;
	}


	/**
	 * 正则匹配取出source中想要取得的部分
	 * 
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

	/**
	 * 判定正则是否匹配source
	 * 
	 * @param regex 正则表达式
	 * @param source 要匹配的数据源
	 * @param boolean 是否匹配
	 */
	public static boolean isMatch(String regex, String source) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);

		while(m.find()) {
			return true;
		}
		return false;
	}

	// 获取随机数用于sleep
	public static long getRandom() {
		long randomLong = randomList.get(new Random().nextInt(3000)) / 6;
		return randomLong;
	}

	/**
	 * 取得n位的随机数组成的字符串
	 */
	public static String getRandomNum(int n) {
		if(n < 1) {
			return "0";
		} else {
			StringBuffer sb = new StringBuffer();
			for(int i = 1; i <= n; i++) {
				double rand = Math.random();
				int random = (int)(rand * 10);
				sb.append(random);
			}

			return sb.toString();
		}
	}

	/**
	 * 去除集合中重复数据
	 * 
	 * @param outgoingUrls 要操作的url集合
	 */
	public static void deleteRepeated(List<String> outgoingUrls) {
		Set<String> set = new HashSet<String>(outgoingUrls);
		outgoingUrls.clear();
		outgoingUrls.addAll(set);

		set.clear();
	}

	/**
	 * 取得总页数
	 * 
	 * @param recordCount 总的评论页数
	 * @param pageSize 每页显示页数
	 * @return pageNums 总页数
	 */
	public static int getPageNum(int recordCount, int pageSize) {
		int size = recordCount / pageSize;
		int mod = recordCount % pageSize;
		if(mod != 0)
			size++;

		return recordCount == 0 ? 1 : size;
	}

	/**
	 * getPage:加载页面
	 * 
	 * @param driver
	 * @param entrance
	 */
	public static boolean getPage(HtmlUnitDriver driver, String entrance) {
		int i = 0;
		while(true) {
			if(i == 5) {
				logger.info("url error : {}", entrance);
				return false;
			}

			try {
				driver.get(entrance);
				Thread.sleep(CrawlerUtils.getRandom());
				return true;
			} catch(Exception e) {
				// e.printStackTrace();
				i++;
				long minutes = i * getRandom() * 30;
				logger.info("get {} url error, fetch again after {} seconds!", entrance, minutes / 1000);
				try {
					Thread.sleep(minutes);
				} catch(InterruptedException e1) {
					// e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 中文轉Unicode
	 */
	public static String toUnicode(String s) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) <= 256) {
				sb.append("\\u00");
			} else {
				sb.append("\\u");
			}
			sb.append(Integer.toHexString(s.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * 通过代理访问<br>
	 * 2014-9-10 <br>
	 * by.kevin
	 * 
	 * @param entrance 入口url
	 * @return 返回成功的使用的代理信息
	 */
	public static String getDriverForProxy(HtmlUnitDriver driver, String entrance, String keyword) {

		while(true) {
			try {
				if(proxy == null) {
					proxy = ClientProxyPool.getInstance().getOneProxy("baidu.com");
				}

				driver.setProxy(proxy.getIp(), proxy.getPort());

				driver.get(entrance);
				String page = driver.getPageSource();

				if(page.contains(keyword)) {
					logger.info("good proxy:{}:{}", proxy.getIp(), proxy.getPort());
					return proxy.getIp() + ":" + proxy.getPort();
				} else {
					logger.info("bad proxy:{}:{}", proxy.getIp(), proxy.getPort());
					ClientProxyPool.getInstance().reportVisitError(proxy, "baidu.com", null);
					proxy = null;
				}
			} catch(Exception e) {
				logger.error("get {} page error : {}", entrance, e.getMessage());
				ClientProxyPool.getInstance().reportVisitError(proxy, "weibo.com", null);
				proxy = null;
			}
		} // end of while

	}

	/**
	 * @Description ipprDecode 解码 用于按明星名字搜索 百度图片url
	 * @param url 解码前url
	 * @return 解码后字符串
	 */
	public static String ipprDecode(String url) {

		String decodeString = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		// 是要执行的JS内容
		try {
			FileReader reader = new FileReader("./conf/ipprDecode.js");

			engine.eval(reader);
			if(engine instanceof Invocable) {
				Invocable invoke = (Invocable)engine;

				// 调用js中的sdecodeHTML方法，并传入参数(多个参数可传)
				decodeString = (String)invoke.invokeFunction("ipprDecode", url);
			}
			reader.close();

		} catch(Exception e) {
			e.printStackTrace();
		}

		return decodeString;
	}

	/**
	 * 通过html构造driver
	 * 
	 * @param html
	 * @return
	 */
	public static HtmlUnitDriver getDriver(final String html) {
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6) {

			@Override
			protected WebClient modifyWebClient(WebClient client1) {

				WebClient client = new WebClient();
				client.setRefreshHandler(new RefreshHandler() {

					@Override
					public void handleRefresh(Page arg0, URL arg1, int arg2) throws IOException {

					}
				});
				client.setTimeout(60 * 1000);
				// 禁止解析css
				client.setCssEnabled(false);
				client.setJavaScriptEnabled(false);
				super.modifyWebClient(client);
				try {
					long time = System.currentTimeMillis();
					String url = "http://iminerLocalhost" + time + ".com";
					client.initialize(HTMLParser.parseHtml(new StringWebResponse(html, new URL(url)), client.getCurrentWindow()));
					client.setHomePage(url);
				} catch(MalformedURLException e) {
					e.printStackTrace();
				} catch(IOException e) {
					e.printStackTrace();
				}
				return client.getCurrentWindow().getWebClient();
			}
		};
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		driver.setJavascriptEnabled(false);

		return driver;
	}

	/**
	 * 执行javaScript语句,可以多行,取result的值
	 * 
	 * @param js 代码
	 * @return result变量的值
	 */
	public static String execJs(String js) {

		String decodeString = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		// 是要执行的JS内容
		try {
			engine.eval(js);
			decodeString = engine.get("result").toString();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return decodeString;
	}

	/**
	 * unicode转为汉字
	 * 
	 * @param s
	 * @return
	 */
	public static String toUnicodeString(String s) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				sb.append("\\u" + Integer.toHexString(c));
			}
		}
		return sb.toString();
	}
}
