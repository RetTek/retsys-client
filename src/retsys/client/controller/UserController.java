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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.User;
import retsys.client.model.Vendor;

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
        AutoCompletionBinding<User> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<User>>() {

            @Override
            public Collection<User> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<User> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("users", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<User>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<User>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }

        }, new StringConverter<User>() {

            @Override
            public String toString(User object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public User fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<User>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<User> event) {
                User user = event.getCompletion();
                //fill other item related fields
                id.setText(splitId(name.getText()) + "");
                name.setText(user.getName());
                password.setText(user.getPassword());
                if("N".equals(user.getUsertype()))
                    usertype.getSelectionModel().select(1);
                else if("A".equals(user.getUsertype()))
                    usertype.getSelectionModel().select(2);
                else
                    usertype.getSelectionModel().clearSelection();
                populateAuditValues(user);
            }
        });
    }    
    
    @Override
    String getSaveUrl() {
        return "users";
    }
 
    @Override
    public Object buildRequestMsg() {
        String request = null;
        
        User user = new User();
        
        user.setName(this.getName().getText());
        user.setPassword(this.getPassword().getText());
        String type = (String)this.getUsertype().getSelectionModel().selectedItemProperty().getValue();
        if("Normal".equals(type))
          user.setUsertype("N");
        else
          user.setUsertype("A");
        
        if (id.getText().equals("")) {
            System.out.println("id is  null... " + id.getText());

        } else {
            user.setId(Integer.valueOf(id.getText()));
        }
        return user;
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

    public String delete(ActionEvent event) throws IOException {
        System.out.println("getId(this.name.getText() .... " + Integer.valueOf(this.id.getText()));
        String response = delete("users", Integer.valueOf(this.id.getText()));

        clear();

        return response;

    }
    
    void clear() {
       id.setText("");
       name.setText("");
       password.setText("");
       usertype.getSelectionModel().select(null);
       this.auditId.setText("");
       this.createdBy.setText("");
       this.createdOn.setValue(null);
       this.modifiedBy.setText("");
       this.modifiedOn.setValue(null);
    }
    
    @Override
    protected void postSave(String response) {
        JsonHelper helper = new JsonHelper();
        System.out.println("response .... " + response);
        try {
            User user = (User) helper.convertJsonStringToObject(response, new TypeReference<User>() {
            });
            id.setText(user.getId().toString());
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
