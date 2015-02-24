/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.main;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javax.json.Json;
import javax.json.JsonWriter;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import retsys.client.controller.ProductController;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;

/**
 *
 * @author Muthu
 */
public class OperationHandler {
    
    
    
    public void OperationHandler(Map jsonMap, String operation, String method){
        String json = "";
        HttpHelper httpHelper = new HttpHelper();
        HttpRequestBase httpPost = new HttpPost();
        
       /* 
        ProductController pController = new ProductController();
        TextField test = new TextField();
        test.setText("Test");
        pController.setName(test);*/
        JsonHelper jsonHelper = new JsonHelper();
        json=jsonHelper.getJsonString(jsonMap);
        System.out.println("Entered Here.. ");
        try{
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(pController.getName());*/
            /*OutputStream out = new ByteArrayOutputStream();
            try (final JsonWriter jsonWriter = Json.createWriter(out)) {
                   jsonWriter.write(Json.createObjectBuilder()
                           .add("name", this.getName().getText())
                           .add("remarks", this.getRemarks().getText())
                           .build()
                   );
               }*/
            HttpClient httpClient = httpHelper.getHttpClient(null);
            //httpPost = httpHelper.getHttpPostObj("products", json);
            if("GET".equals(method))
                httpPost = httpHelper.getHttpGetObj(operation, json);
            else if("POST".equals(method))
                httpPost = httpHelper.getHttpPostObj(operation, json);
            
            httpPost.addHeader("Method", method);
            httpPost.addHeader("Accept", "application/json");
            System.out.println("URL : " + httpPost.getURI().toString());
            
            HttpResponse response = httpClient.execute(httpPost);
            
            
            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
            
            System.out.println("JSON Object : " +json);
            System.out.println("Response : " +response.getStatusLine().getStatusCode());
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
