/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package retsys.client.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import org.apache.http.impl.client.HttpClients;
import retsys.client.http.HttpHelper;

/**
 *
 * @author ranju
 */
public abstract class StandardController {
    public void init(){
        
    }
    
    public String save(ActionEvent event) throws IOException{
        String jsonRequest = buildRequestMsg();
        String response = null;
        
        HttpHelper helper = new HttpHelper();
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpPostObj(getSaveUrl(), jsonRequest));
        
        return response;
    }
    
    abstract String buildRequestMsg();
    abstract String getSaveUrl();
}
