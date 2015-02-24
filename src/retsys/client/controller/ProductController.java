/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package retsys.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.concurrent.Service;
import javafx.scene.control.TextField;
import javax.json.Json;
import javax.json.JsonWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.TextArea;
import java.io.StringWriter;
import java.util.HashMap;
import javafx.concurrent.Task;
/**
 * FXML Controller class
 *
 * @author ranju
 */
//public class ProductController implements Initializable {
public class ProductController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private TextArea remarks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void processProduct(ActionEvent event){
        String json = "";
        HttpHelper httpHelper = new HttpHelper();
        HttpPost httpPost;
        
        ProductController pController = new ProductController();
        TextField test = new TextField();
        test.setText("Test");
        pController.setName(test);
        
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
            Map<String,Object> reqMap = new HashMap<String,Object>();
            reqMap.put("name", this.getName().getText());
            reqMap.put("remarks", this.getRemarks().getText());
            
            JsonHelper jsonHelper = new JsonHelper();
            json=jsonHelper.getJsonString(reqMap);
            
            HttpClient httpClient = httpHelper.getHttpClient(null);
            httpPost = httpHelper.getHttpPostObj("products", json);
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
            
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

            
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void processProductQuery(ActionEvent event){
        
        HttpHelper httpHelper = new HttpHelper();
        HttpGet httpGet;
        
        ProductController pController = new ProductController();
        TextField test = new TextField();
        test.setText("Test");
        pController.setName(test);
        
        System.out.println("Entered Here.. ");
        try{
            
            HttpClient httpClient = httpHelper.getHttpClient(null);
            httpGet = httpHelper.getHttpGetObj("products/name/"+this.getName().getText());
            httpGet.addHeader("Method", "GET");
            httpGet.addHeader("Accept", "application/json");
            System.out.println("URL : " + httpGet.getURI().toString());
            
            HttpResponse response = httpClient.execute(httpGet);
            
            
            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
            
            
            System.out.println("Response : " +response.getStatusLine().getStatusCode());
            
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line ="";
            String jsonString = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                jsonString = jsonString + line;
            }
            //StringWriter sw = new StringWriter();
            //sw.write(jsonString);
            JsonHelper jsonHelper = new JsonHelper();
            Map <String,Object>[] jsonMap = jsonHelper.getJsonMapArray(jsonString);
            
            this.getName().setText((String)jsonMap[0].get("name"));
            this.getRemarks().setText((String)jsonMap[0].get("remarks"));
            
            /*ObjectMapper mapper = new ObjectMapper();
            ProductController dummy = mapper.readValue(jsonString.getBytes(),ProductController.class);
            
            this.getName().setText(dummy.getName().getText());
            this.getRemarks().setText(dummy.getRemarks().getText());*/
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
        private class MyService extends Service<Void> {
            ProductController pController;
            public MyService(ProductController controller){
                pController = controller;
            }
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        HttpHelper httpHelper = new HttpHelper();
                        HttpGet httpGet;                        
                        HttpClient httpClient = httpHelper.getHttpClient(null);
                        httpGet = httpHelper.getHttpGetObj("products/name/"+pController.getName().getText());
                        httpGet.addHeader("Method", "GET");
                        httpGet.addHeader("Accept", "application/json");
                        System.out.println("URL : " + httpGet.getURI().toString());


                        HttpResponse response = httpClient.execute(httpGet); 
                        if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatusLine().getStatusCode());
                        }


                        System.out.println("Response : " +response.getStatusLine().getStatusCode());

                        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
                        String line ="";
                        String jsonString = "";
                        while ((line = rd.readLine()) != null) {
                            System.out.println(line);
                            jsonString = jsonString + line;
                        }
                        //StringWriter sw = new StringWriter();
                        //sw.write(jsonString);
                        JsonHelper jsonHelper = new JsonHelper();
                        Map <String,Object>[] jsonMap = jsonHelper.getJsonMapArray(jsonString);

                        /*this.getName().setText((String)jsonMap[0].get("name"));
                        this.getRemarks().setText((String)jsonMap[0].get("remarks"));
                        return "";*/
                        return null;
                    }
                };
            }
    }

    
    /**
     * @return the name
     */
    public TextField getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(TextField name) {
        this.name = name;
    }

    /**
     * @return the remarks
     */
    public TextArea getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(TextArea remarks) {
        this.remarks = remarks;
    }
    
}
