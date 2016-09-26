package com.netease.welkin.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * @author zhangzp
 */
public final class JsonUtil {
    private JsonUtil() {
    }

    private static Log log = LogFactory.getLog(JsonUtil.class);
    private static ObjectMappingCustomer objectMappingCustomer = null;
    private static ObjectMapper mapper = null;

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            mapper = new ObjectMapper();
            mapper.setDateFormat(df);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        return mapper;
    }

    private static ObjectMappingCustomer getObjectMapperCustomer() {
        if (objectMappingCustomer == null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            objectMappingCustomer = new ObjectMappingCustomer();
            objectMappingCustomer.setDateFormat(df);
        }
        return objectMappingCustomer;
    }

    /**
     * From json string to bean.
     * 
     * @param <T> <T>
     * @param json json
     * @param clazz clazz
     * @return <T>
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        if (!StringUtils.isEmpty(json)) {
            ObjectMapper mapperin = getObjectMapper();
            try {
                return mapperin.readValue(json, clazz);
            } catch (Exception e) {
                log.error("JSONString : " + json, e);
            }
        }
        return null;
    }

    /**
     * From json string to bean list.
     * 
     * @param <T> <T>
     * @param json json
     * @param clazz clazz
     * @return ArrayList<T>
     */
    public static <T> ArrayList<T> toBeanList(String json, Class<T> clazz) {
        if (!StringUtils.isEmpty(json)) {
            ObjectMapper mapperin = getObjectMapper();
            try {
                TypeFactory typeFactory = TypeFactory.defaultInstance();
                return mapperin.readValue(json,
                        typeFactory.constructCollectionType(ArrayList.class, clazz));
            } catch (Exception e) {
                log.error("JSONString : " + json, e);
            }
        }
        return null;
    }

    /**
     * To json string.
     * 
     * @param object object
     * @return String
     */
    public static String toString(Object object) {
        ObjectMapper mapperin = getObjectMapper();
        return toString(object, mapperin);
    }

    public static String toEmptyString(Object object) {
        ObjectMappingCustomer mapperin = getObjectMapperCustomer();
        try {
            return mapperin.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e, e);
            return "";
        }
    }

    /**
     * To json string base on given mapper.
     * 
     * @param object object
     * @param mapper mapper
     * @return String
     */
    public static String toString(Object object, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(e, e);
            return "";
        }
    }
    
    
    public static JsonNode getNode(Object object,String ... fieldNames) throws Exception{
    	
    	String json = null;
    	ObjectMapper mapper = getObjectMapper();
    	if(object instanceof String){
    		 json = (String)object;
    	}else{
             json = JsonUtil.toString(object);  
    	}
    	
		JsonNode node = mapper.readTree(json);
        for(String fieldName : fieldNames){
        	 node = node.findValue(fieldName.toString());
        }
      
    	return node;
    }
    
    /**
     * To json string.
     * 处理对象中某些键值对的value值是json串的情况
     * 
     * @param Object object
     * @param List<String> fieldNames
     * @return String
     */
    public static String toString(Object object, List<String> fieldNames) {
    	ObjectMapper mapper = getObjectMapper();
    	ObjectNode objectNode = mapper.valueToTree(object);  // 将java对象转换为json对象
    	for (String fieldName: fieldNames) {
    		if (objectNode.get(fieldName) != null) {
    			String str =  objectNode.get(fieldName).asText(); // 获取json字符串
    			 try {
					JsonNode singleNode = mapper.readTree(str);	// 将json字符串转为json对象
					objectNode.put(fieldName, singleNode);		// 将对应位置的json字符串替换为json对象
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
    		}
    	}
    	return objectNode.toString();
    }
    
}
