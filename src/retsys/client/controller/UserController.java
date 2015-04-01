/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import retsys.client.json.JsonHelper;
import retsys.client.model.Client;
import retsys.client.model.User;

/**
 * FXML Controller class
 *
 * @author Fahad
 */
public class UserController extends StandardController implements Initializable {
    
    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox usertype;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    String getSaveUrl() {
        return "users";
    }
 
    @Override
    public String buildRequestMsg() {
        String request = null;
        
        User user = new User();
        
        user.setName(this.getName().getText());
        user.setPassword(this.getPassword().getText());
        String type = (String)this.getUsertype().getSelectionModel().selectedItemProperty().getValue();
        if("Normal".equals(type))
          user.setUsertype("N");
        else
          user.setUsertype("A");
        
        JsonHelper helper = new JsonHelper();
        request = helper.getJsonString(user);
        
        return request;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public ChoiceBox getUsertype() {
        return usertype;
    }

    public void setUsertype(ChoiceBox usertype) {
        this.usertype = usertype;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

}
