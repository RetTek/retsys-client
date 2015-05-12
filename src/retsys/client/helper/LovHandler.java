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
    private int startPosition;
    private int maxResults;

    public LovHandler(String entity, String conditionFld) {
        this.entity = entity;
        this.conditionFld = conditionFld;
    }

    public LovHandler(String entity, String conditionFld, int startPosition, int maxResults) {
        this.entity = entity;
        this.conditionFld = conditionFld;
        this.startPosition = startPosition;
        this.maxResults = maxResults;
    }

    public String getSuggestions(String filterOn) {
        String response = null;
        HttpHelper helper = new HttpHelper();
        StringBuilder queryString = new StringBuilder(entity);
        try {
            if (conditionFld != null && filterOn != null) {
                queryString.append("/" + conditionFld + "/" + filterOn);
            }

            if (startPosition > 0 && maxResults > 0) {
                queryString.append("?start=" + startPosition + "&max=" + maxResults);
            }

            response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpGetObj(queryString.toString()));
        } catch (IOException ex) {
            Logger.getLogger(LovHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

}
