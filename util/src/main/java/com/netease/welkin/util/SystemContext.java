package com.netease.welkin.util;


/**
 * 
 * @author bjliuzezhou
 * @description 使用threadLocal 封装分页所必需传的参数  
 * @date 2016年7月1日
 */
public class SystemContext {
	//当前第几页
	private static ThreadLocal<Integer> currentPage = new ThreadLocal<Integer>();
	//总共的页数
	private static ThreadLocal<Integer> totalPages = new ThreadLocal<Integer>();
	//起始数据位置
	private static ThreadLocal<Integer> offSet = new ThreadLocal<Integer>();
	//总记录的条数
	private static ThreadLocal<Integer> recordCount = new ThreadLocal<Integer>(); 
	//每页显示数
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>(); 
 
    /* 
  	 * currentPage ：get、set、remove 
	 */ 
	public static int getCurrentPage(){
		Integer cp = currentPage.get();
		if(cp == null){
			return 0;
		}
		return cp;
	}
	
	public static void setCurrentPage(int currentPage) {
	
		int validPage = currentPage > 0 ? currentPage : 1;
		validPage = validPage < getTotalPages() ? validPage : getTotalPages();
		SystemContext.currentPage.set(validPage);
		
	}
	public static void removeCurrentPage(){
		currentPage.remove();
	}
	
	/* 
	 * totalPages ：get、set、remove 
	 */  
	public static int getTotalPages() {  
	    Integer tp = totalPages.get();  
	    if (tp == null) {  
	        return 0;  
	    }  
	    return tp;  
	}  
	
	public static void calTotalPages() {  
		
		int totalPages = (getRecordCount() + getPageSize() -1) / getPageSize();
	    SystemContext.totalPages.set(totalPages);  
	}  
	
	public static void removeTotalPages(){  
	    totalPages.remove();  
	}  
	
	/* 
	 * offset ：get、set、remove 
	 */  
	public static int getOffSet() {  
	    Integer os =offSet.get();  
	    if (os == null) {  
	        return 0;  
	    }  
	    return os;  
	}  
	
	public static void calOffSet() {  
		int offset = (getCurrentPage() - 1) * getPageSize();
		int validOffSet = offset > 0 ? offset : 0;
		SystemContext.offSet.set(validOffSet);
	}  
	
	
	public static void removeOffSet(){  
	    offSet.remove();  
	}   
	
	/* 
  	 * recordCount ：get、set、remove 
	 */ 
	public static int getRecordCount(){
		Integer rc = recordCount.get();
		if(rc == null){
			return 0;
		}
		return rc;
	}
	
	public static void setRecordCount(int recordCount) {
		
		SystemContext.recordCount.set(recordCount);
	}
	public static void removeRecordCount(){
		recordCount.remove();
	}
	 
	/* 
  	 * pageSize ：get、set、remove 
	 */ 
	public static int getPageSize(){
		Integer ps = pageSize.get();
		if(ps == null){
			return 0;
		}
		return ps;
	}
	
	public static void setPageSize(int pageSize) {
		
		SystemContext.pageSize.set(pageSize);
	}
	public static void removePageSize(){
		pageSize.remove();
	}
	
	
	public static void PageFilter(int recordCount,int pageSize,int currentPage){
    	// 记录总条数
		SystemContext.setRecordCount(recordCount);
		SystemContext.setPageSize(8);
		SystemContext.calTotalPages();
		SystemContext.setCurrentPage(currentPage);
		SystemContext.calOffSet();
    }
}
