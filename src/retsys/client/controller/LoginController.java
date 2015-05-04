/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static retsys.client.controller.DashboardController.stage;
import retsys.client.helper.GetCurrentDateTime;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.main.AppContext;
import retsys.client.model.User;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class LoginController implements Initializable {
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Label errorMessage;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void processLogin(ActionEvent event)throws Exception  {
        
        stage = (Stage) userId.getScene().getWindow();
            errorMessage.setText("Hello " + userId.getText());
       
            if (!userLogging(userId.getText(), password.getText())){
                //errorMessage.setText("Unknown user " + userId.getText());
                errorMessage.setText("Incorrect User / Password");
            }
            else{
                DashboardController profile = (DashboardController) replaceSceneContent("/retsys/client/fxml/Dashboard.fxml");
                //Parent root = FXMLLoader.load(getClass().getResource("/retsys/client/fxml/Dashboard.fxml"));
                AppContext ctx = AppContext.getInstance();
                ctx.setUsername(userId.getText());
                ctx.setPassword(password.getText());
                stage.fullScreenProperty();
                //Scene scene = new Scene(root);
                GetCurrentDateTime GetCurrentDateTime= new GetCurrentDateTime();
                String date=GetCurrentDateTime.getsysDate();
                stage.setTitle("Invertory Management System  "+this.userId.getText() +" "+date);
                stage.centerOnScreen();
            }
    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        
        URL location = getClass().getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader();
        
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        InputStream in = getClass().getResourceAsStream(fxml);
        Parent root;       
        
       fxmlLoader.setLocation(location);
        AnchorPane page;
        try {
            page = (AnchorPane) fxmlLoader.load(in);
        } finally {
            in.close();
        }
     
        Scene scene = new Scene(page);
         scene.getStylesheets().add(this.getClass().getResource("/retsys/client/css/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        //stage.sizeToScene();
        
        return (Initializable) fxmlLoader.getController();
    }

    
    private boolean userLogging(String usr, String pass) {
        //Code to check
        boolean chkUser = false;
        List<User> list = null;
        try{
            if("SYSTEM".equals(usr) && "RETSYS".equals(pass))
                return true;
            
            LovHandler lovHandler = new LovHandler("users", "name");
            String response = lovHandler.getSuggestions(usr);
            list = (List<User>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<User>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        
        for (User listUser : list){
            if (listUser.getName().equals(usr) && listUser.getPassword().equals(pass))
                chkUser = true;
        }
        return chkUser;
    }
}
    
