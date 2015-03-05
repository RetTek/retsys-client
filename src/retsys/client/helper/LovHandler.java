/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.helper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.impl.client.HttpClients;
import retsys.client.http.HttpHelper;

/**
 *
 * @author ranju
 */
public class LovHandler {

    private String entity;
    private String conditionFld;

    public LovHandler(String entity, String conditionFld) {
        this.entity = entity;
        this.conditionFld = conditionFld;
    }

    public String getSuggestions(String filterOn) {
        String response = null;
        HttpHelper helper = new HttpHelper();
        try {
            response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpGetObj(entity+"/"+conditionFld+"/" + filterOn));
        } catch (IOException ex) {
            Logger.getLogger(LovHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

}
