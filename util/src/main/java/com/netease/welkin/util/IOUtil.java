package com.netease.welkin.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class IOUtil {
	public static byte[] getImgFileToByte(File file) {
		
        byte[] b = new byte[1024];
        byte[] bb = null;
        try {
        	InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int len;
            //将输入流中最多 1024个数据字节读入 byte 数组。尝试读取 1024 个字节，但读取的字节也可能小于该值,以整数形式返回实际读取的字节数
            while((len=is.read(b,0,b.length))!=-1){
            	 bytestream.write(b);
           }
            //创建一个新分配的字节数组,数组的大小是当前输出流的大小，内容是当前输出流的拷贝。
             bb = bytestream.toByteArray();
            
            bytestream.close();
            is.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bb;
    }
	
	public static void copy(MultipartFile s,File t) {
		InputStream fis = null;
		OutputStream fos = null;
		
		try {
			fis = new BufferedInputStream(s.getInputStream());
			fos = new BufferedOutputStream(new FileOutputStream(t));
			byte[] buf = new byte[1024];
			int i;
			while(( i = fis.read(buf)) != -1){
				fos.write(buf, 0, i);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
