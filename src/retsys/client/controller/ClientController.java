/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
 * @author Muthu
 */
public class ClientController implements Initializable {
    @FXML
    private Tab tab_vendor;
    @FXML
    private Label lbl_client_name;
    @FXML
    private TextField name;
    @FXML
    private Label lbl_client_remarks;
    @FXML
    private Label lbl_client_address;
    @FXML
    private TextArea remarks;
    @FXML
    private TextArea address;
    @FXML
    private TextField phone;
    @FXML
    private Label lbl_client_phone;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private Label lbl_client_mobile;
    @FXML
    private Label lbl_client_email;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_edit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    public void processClient(ActionEvent event){
        String json = "";
        OperationHandler opthandler = new OperationHandler();      


        
        System.out.println("Entered Here.. ");
        try{
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(pController.getName());*/
            Map<String,Object> reqMap = new HashMap<String,Object>();
            
            reqMap.put("name", this.getName().getText());
            reqMap.put("address", this.getAddress().getText());
            reqMap.put("phone", this.getPhone().getText());
            reqMap.put("mobile", this.getMobile().getText());
            reqMap.put("remarks", this.getRemarks().getText());
            
            opthandler.OperationHandler(reqMap,"clients","POST");
            
   } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
