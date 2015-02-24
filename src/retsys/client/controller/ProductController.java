/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package retsys.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javax.json.Json;
import javax.json.JsonWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.main.OperationHandler;

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
        OperationHandler opthandler = new OperationHandler();
        HttpPost httpPost;
        
        ProductController pController = new ProductController();
        TextField test = new TextField();
        //test.setText("Test");
        //pController.setName(test);
        
        System.out.println("Entered Here.. ");
        try{
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(pController.getName());*/
            OutputStream out = new ByteArrayOutputStream();
            try (final JsonWriter jsonWriter = Json.createWriter(out)) {
                   jsonWriter.write(Json.createObjectBuilder()
                           .add("name", this.getName().getText())
                           .add("remarks", this.getRemarks().getText())
                           .build()
                   );
               }
            opthandler.OperationHandler(out,"Product","Create");
            
        } catch(Exception e){
            e.printStackTrace();
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
