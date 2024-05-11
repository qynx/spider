package xyz.zix.spider.repo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @SneakyThrows
    public static String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj,  new TypeReference<Map<String, Object>>() {
        });
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> type) {
        if (null == json || json.isEmpty()) {
            return null;
        }
        return (T) objectMapper.readValue(json, type);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (null == json || json.isEmpty()) {
            return null;
        }
        return (T) objectMapper.readValue(json, typeReference);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Type type) {
        return (T) objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(type));
    }

}
