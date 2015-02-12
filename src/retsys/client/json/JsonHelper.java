/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ranju
 */
public class JsonHelper {

    private String message;
    private ObjectMapper mapper = new ObjectMapper();

    public JsonHelper(String message) {
        this.message = message;
    }
    
    
}
