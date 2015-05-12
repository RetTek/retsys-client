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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class VendorController extends StandardController implements Initializable {

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

        AutoCompletionBinding<Vendor> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Vendor>>() {

            @Override
            public Collection<Vendor> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Vendor> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("vendors", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Vendor>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Vendor>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(VendorController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }

        }, new StringConverter<Vendor>() {

            @Override
            public String toString(Vendor object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Vendor fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Vendor>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Vendor> event) {
                Vendor vendor = event.getCompletion();
                //fill other item related fields
                id.setText(splitId(name.getText()) + "");
                name.setText(vendor.getName());
                address.setText(vendor.getAddress());
                phone.setText(vendor.getPhone());
                mobile.setText(vendor.getMobile());
                remarks.setText(vendor.getRemarks());
                email.setText(vendor.getEmail());

                populateAuditValues(vendor);
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

    @Override
    Object buildRequestMsg() {
        String message = null;

        Vendor vendorObj = new Vendor();
        vendorObj.setName(name.getText());
        vendorObj.setAddress(address.getText());
        vendorObj.setMobile(mobile.getText());
        vendorObj.setPhone(phone.getText());
        vendorObj.setRemarks(remarks.getText());
        vendorObj.setEmail(email.getText());

        System.out.println("getId(this.name.getText() .... " + id.getText());
        if (id.getText().equals("")) {
            System.out.println("id is  null... " + id.getText());

        } else {
            vendorObj.setId(Integer.valueOf(id.getText()));
        }

        return vendorObj;
    }

    @Override
    String getSaveUrl() {
        return "vendors";
    }

    void clear() {

        name.setText("");
        this.address.setText("");
        this.email.setText("");
        this.phone.setText("");
        this.mobile.setText("");
        this.remarks.setText("");
        this.id.setText("");

    }

    public String delete(ActionEvent event) throws IOException {
        System.out.println("getId(this.name.getText() .... " + splitId(this.name.getText()));
        String response = delete("vendors", Integer.valueOf(this.id.getText()));

        clear();

        return response;

    }

    @Override
    protected void postSave(String response) {
        JsonHelper helper = new JsonHelper();
        System.out.println("response .... " + response);
        try {
            Vendor vendor = (Vendor) helper.convertJsonStringToObject(response, new TypeReference<Vendor>() {
            });
            id.setText(vendor.getId().toString());
        } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
