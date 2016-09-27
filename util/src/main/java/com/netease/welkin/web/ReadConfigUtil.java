package com.netease.welkin.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;



/** 
 * 配置文件读取工具类
 * 
 * @author zel
 *  
 */   
public class ReadConfigUtil {
	
  
	 Logger  log = Logger.getLogger(ReadConfigUtil.class);
	
	public InputStream in = null;
	public BufferedReader br = null;
	private Properties config = null;
	/**
	 * 如果所读的文件为文本类型而为key/value型式的话，直接get该属性即可
	 */
	private String lineConfigTxt;

	public String getLineConfigTxt() {
		return lineConfigTxt;
	}

	public void setLineConfigTxt(String lineConfigTxt) {
		this.lineConfigTxt = lineConfigTxt;
	}

	// 此时的configFilePath若是非普通文件，即properties文件的话，要另行处理
	public ReadConfigUtil(String configFilePath, boolean isConfig) {
		in = ReadConfigUtil.class.getClassLoader().getResourceAsStream(
				configFilePath);
		try {
			if (isConfig) {
				config = new Properties();
				config.load(in);
				in.close();
				
				//log.info("抓取配置文件成功加载=="+configFilePath);
			} else {
				br = new BufferedReader(new InputStreamReader(in));
				this.lineConfigTxt = getTextLines();
				//log.info("种子文件成功加载=="+configFilePath);
			}
		} catch (IOException e) {
			log.info("加载配置文件时，出现问题!=="+configFilePath);
		}
	}

	public String getValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("ConfigInfoError" + e.toString());
			return null;
		}
	}

	private String getTextLines() {
		StringBuilder sb = new StringBuilder();
		String temp = null;
		try {
			while ((temp = br.readLine()) != null) {
				if (temp.trim().length() > 0 && (!temp.trim().startsWith("#"))) {
					sb.append(temp);
					sb.append("\n");
				}
			}
			br.close();
			in.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取slaves文件时，出现问题!");
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		// ReadConfigUtil readConfig=new ReadConfigUtil("slaves");
		// System.out.println(readConfig.getTextLines());
	}
}
