package com.khlebtsov.kalories.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapperToJsonInit() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.setFailOnUnknownId(false);
        mapper.setFilterProvider(simpleFilterProvider);
        return mapper;
    }

    private static ObjectMapper mapperToObjectInit() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.setFailOnUnknownId(false);
        mapper.setFilterProvider(simpleFilterProvider);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private static ObjectMapper mapperToJson = mapperToJsonInit();
    private static ObjectMapper mapperToObject = mapperToObjectInit();


    private JsonUtil() {
    }


    public static String toJson(Object object) {

        String s = null;
        try {
            s = mapperToJson.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Can not convert to json: {}", e);
        }
        return s;
    }

    public static <T> T toObject(String jsonInString, Class<T> aClass) {

        T object = null;
        try {
            object = mapperToObject.readValue(jsonInString.getBytes(), aClass);
        } catch (IOException e) {
            logger.error("Can not construct object from json {}", e);
        }
        return object;
    }
}
