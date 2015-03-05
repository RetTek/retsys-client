/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.json.Json;
import javax.json.JsonWriter;
import retsys.client.json.JsonHelper;
import retsys.client.main.OperationHandler;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class VendorController extends StandardController implements Initializable {

    @FXML
    private Tab tab_vendor;
    @FXML
    private Label lbl_vendor_name;
    @FXML
    private TextField name;
    @FXML
    private Label lbl_vendor_remarks;
    @FXML
    private Label lbl_vendor_address;
    @FXML
    private TextArea remarks;
    @FXML
    private TextArea address;
    @FXML
    private TextField phone;
    @FXML
    private Label lbl_vendor_phone;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private Label lbl_vendor_mobile;
    @FXML
    private Label lbl_vendor_email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void processVendor(ActionEvent event) {

        String json = "";
        OperationHandler opthandler = new OperationHandler();

        System.out.println("Entered Here.. ");
        try {
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
             json = ow.writeValueAsString(pController.getName());*/
            OutputStream out = new ByteArrayOutputStream();
            try (final JsonWriter jsonWriter = Json.createWriter(out)) {
                jsonWriter.write(Json.createObjectBuilder()
                        .add("name", this.getName().getText())
                        .add("address", this.getAddress().getText())
                        .add("phone", this.getPhone().getText())
                        .add("mobile", this.getMobile().getText())
                        .add("remarks", this.getRemarks().getText())
                        .build());
            }
            Map reqMap = new HashMap();
            opthandler.OperationHandler(reqMap, "Vendor", "Create");
        } catch (Exception e) {
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

    @Override
    String buildRequestMsg() {
        String message = null;
        
        Vendor vendorObj = new Vendor();
        vendorObj.setName(name.getText());
        vendorObj.setAddress(address.getText());
        vendorObj.setMobile(mobile.getText());
        vendorObj.setPhone(phone.getText());
        vendorObj.setRemarks(remarks.getText());
        
        message = new JsonHelper().getJsonString(vendorObj);

        return message;
    }

    @Override
    String getSaveUrl() {
        return "vendors";
    }

}
