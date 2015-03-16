/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.Client;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class ClientController extends StandardController implements Initializable{
    @FXML
    private TextField name;
    @FXML
    private TextArea address;
    @FXML
    private TextField phone;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() 
        AutoCompletionBinding<Client> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() 
        {

            @Override
            public Collection<Client> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Client> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("clients", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Client>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Client>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
            
            
        }, new StringConverter<Client>() {

            @Override
            public String toString(Client object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Client fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Client>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Client> event) {
                Client client = event.getCompletion();
                //fill other item related fields
                phone.setText(client.getPhone());
                address.setText(client.getAddress());
                mobile.setText(client.getMobile());
                remarks.setText(client.getRemarks());
                email.setText(client.getEmail());
                
            }
        });
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

    /**
     * @return the address
     */
    public TextArea getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(TextArea address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public TextField getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(TextField phone) {
        this.phone = phone;
    }

    /**
     * @return the mobile
     */
    public TextField getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(TextField mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public TextField getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(TextField email) {
        this.email = email;
    }
    
    @Override
    public String buildRequestMsg() {
        String request = null;
        
        Client client = new Client();
        
        client.setName(this.getName().getText());
        client.setAddress(this.getAddress().getText());
        client.setMobile(this.getMobile().getText());
        client.setPhone(this.getPhone().getText());
        client.setRemarks(this.getRemarks().getText());
        client.setEmail(email.getText());
        
        JsonHelper helper = new JsonHelper();
        request = helper.getJsonString(client);
        
        return request;
    }
    
    public String getSaveUrl(){
        
        return "clients";
        
    }
    
}
