/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package retsys.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import javafx.scene.control.TextArea;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
//import retsys.client.model.Client;
import retsys.client.model.Product;
/**
 * FXML Controller class
 *
 * @author ranju
 */
//public class ProductController implements Initializable {
public class ProductController extends StandardController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private TextArea remarks;
    
     @FXML
    private TextField productDesc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        AutoCompletionBinding<Product> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Product>>() 
        {

            @Override
            public Collection<Product> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Product> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("products", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Product>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Product>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
            
            
        }, new StringConverter<Product>() {

            @Override
            public String toString(Product object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Product fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Product>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Product> event) {
                Product product = event.getCompletion();
                //fill other item related fields
                name.setText(product.getName());
                remarks.setText(product.getRemarks());
                //desc.setText(product.getDesc());
                
                populateAuditValues(product);
            }
        });
    }    
    
   

    @Override
    public Object buildRequestMsg() {
        String request = null;
        
        Product product = new Product();
        
        product.setName(this.getName().getText());
        product.setProdDesc(this.getDesc().getText());
        product.setRemarks(this.getRemarks().getText());
        
//        JsonHelper helper = new JsonHelper();
//        request = helper.getJsonString(product);
        
        return product;
    }

   public String getSaveUrl(){
        
        return "products";
        
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
     * @return the name
     */
    public TextField getDesc() {
        return productDesc;
    }

    /**
     * @param desc the name to set
     */
    public void setDesc(TextField desc) {
        this.productDesc = productDesc;
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
    
    public  String delete(ActionEvent event) throws IOException {
         System.out.println("getId(this.name.getText() .... "+splitId(this.name.getText()));
        String response = delete("products",splitId(this.name.getText()));
        
        clear();

        return response;
       
    }
    void clear() {
    
       name.setText("");
       this.productDesc.setText("");
        this.remarks.setText("");
        
     
    }
    
}
