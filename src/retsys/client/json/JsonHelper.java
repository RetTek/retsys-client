/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ranju
 */
public class JsonHelper {

    private String message;
    private ObjectMapper mapper;

    public JsonHelper(String message) {
        this.message = message;
        mapper = new ObjectMapper();
    }

    public JsonHelper() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    public String getJsonString(Object object) {
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public Map<String, Object> getJsonMap(String jsonString) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            jsonMap = mapper.readValue(jsonString,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    public Map<String, Object>[] getJsonMapArray(String jsonString) {
        Map<String, Object>[] jsonMap = null;//new HashMap[]<String,Object>();
        try {
            jsonMap = mapper.readValue(jsonString,
                    new TypeReference<HashMap<String, Object>[]>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonMap;
    }
    
    public Object convertJsonStringToObject(String json, TypeReference ref) throws IOException{
        return mapper.readValue(json, ref);
    }
    
}
