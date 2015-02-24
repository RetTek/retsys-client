/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.main;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javax.json.Json;
import javax.json.JsonWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import retsys.client.controller.ProductController;
import retsys.client.http.HttpHelper;

/**
 *
 * @author Muthu
 */
public class OperationHandler {
    
    
    
    public void OperationHandler(OutputStream out, String operation, String action){
        String json = "";
        HttpHelper httpHelper = new HttpHelper();
        HttpPost httpPost;
        
       /* 
        ProductController pController = new ProductController();
        TextField test = new TextField();
        test.setText("Test");
        pController.setName(test);*/
        
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
            json=out.toString();
            
            HttpClient httpClient = httpHelper.getHttpClient(null);
            httpPost = httpHelper.getHttpPostObj("products", json);
            if(operation=="Product"){
            httpPost = httpHelper.getHttpPostObj("products", json);
            }
            if(operation=="Client"){
            httpPost = httpHelper.getHttpPostObj("clients", json);
            }
            
            if(operation=="Vendor"){
            httpPost = httpHelper.getHttpPostObj("vendors", json);
            }
            if(operation=="Project"){
            httpPost = httpHelper.getHttpPostObj("projects", json);
            }
            
             httpPost.addHeader("Method", "POST");
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
