package com.proquest.ipa.automation.framework.tools.utils;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;


public final class JsonUtils {

    private static JsonUtils instance = null;

    private ObjectMapper mapper;

    public JsonUtils() {
        mapper = new ObjectMapper();
    }

    public static JsonUtils getInstance() {
        if (instance == null) {
            instance = new JsonUtils();
        }
        return instance;
    }


    public static String extractContentFromFile(String fileName) {
        InputStream is = JsonUtils.class.getResourceAsStream("/files/" + fileName);
        String content = "";
        try {
            content = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String serialize(Object object) {

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Object deserialize(String json, Class objectClass) {

        try {
            return mapper.readValue(json, objectClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public <T> T deserialize(String json, TypeReference<T> type) {

        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Converts the response entity to plain json
     * @param responseEntity
     * @return
     */
    public String getJsonBody(HttpEntity responseEntity){
        try{
            return EntityUtils.toString(responseEntity);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }
}
