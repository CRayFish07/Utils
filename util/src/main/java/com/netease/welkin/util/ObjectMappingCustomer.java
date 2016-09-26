package com.netease.welkin.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Date: 13-11-13 Time: 上午9:01
 */
public class ObjectMappingCustomer extends ObjectMapper {

    public ObjectMappingCustomer() {
        super();
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {

            @Override
            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
                    throws IOException, JsonProcessingException {
                jg.writeString("");
            }
        });

    }
}
