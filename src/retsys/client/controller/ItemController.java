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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.model.Item;
import retsys.client.model.Model;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author ranju
 */
public class ItemController extends StandardController implements Initializable {

    @FXML
    private Tab tab_item;
    @FXML
    private TextField brand;
    @FXML
    private Label lbl_item_brand;
    @FXML
    private Label lbl_item_name;
    @FXML
    private TextField item_name;
    @FXML
    private Label lbl_item_remarks;
    @FXML
    private Label lbl_rate;
    @FXML
    private TextArea item_remarks;
    @FXML
    private TextField quantity;
    @FXML
    private Label lbl_item_unit;
    @FXML
    private TextField unit;
    @FXML
    private TextField color;
    @FXML
    private Label lbl_item_color;
    @FXML
    private Label lbl_billno;
    @FXML
    private TextField rate;
    @FXML
    private Label lbl_quantity;
    @FXML
    private TextField size;
    @FXML
    private Label lbl_size;
    @FXML
    private Label lbl_vendor;
    @FXML
    private TextField vendor;
    @FXML
    private TextField billno;
    @FXML
    private TextField supervisor;
    @FXML
    private Label lbl_supervisor;
    @FXML
    private TextField godown_name;
    @FXML
    private TextField Site;
    @FXML
    private TextField location1;
    @FXML
    private TextField location2;
    @FXML
    private TextField location3;
    @FXML
    private TextField transport_mode;
    @FXML
    private TextField transport_charges;
    @FXML
    private TextField drawer_no;
    @FXML
    private Label lbl_site;
    @FXML
    private Label lbl_godown;
    @FXML
    private Label lbl_location;
    @FXML
    private Label lbl_supervisor1;
    @FXML
    private Label lbl_section;
    @FXML
    private Label lbl_drawer_no;
    @FXML
    private Label lbl_transport_mode;
    @FXML
    private Label lbl_supervisor2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(vendor, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Vendor>>() {

            @Override
            public Collection<Vendor> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Vendor> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("vendors", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Vendor>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Vendor>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
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

    }

    @Override
    String buildRequestMsg() {
        Item item = new Item();
        Vendor vendorObj = new Vendor();

        item.setName(item_name.getText());
        item.setRate(Double.parseDouble(rate.getText()));
        item.setBrand(brand.getText());
        item.setColor(color.getText());
        item.setUnit(unit.getText());
        item.setSize(size.getText());
        item.setBillno(billno.getText());
        item.setSite(Site.getText());
        item.setRemarks(item_remarks.getText());
        item.setQuantity(Double.parseDouble(quantity.getText()));
        item.setTransportmode(transport_mode.getText());
        item.setTransportcharge(Double.parseDouble(transport_charges.getText()));
        item.setSupervisor(supervisor.getText());

        vendorObj.setId(getId(vendor.getText()));
        item.setVendor(vendorObj);

        return new JsonHelper().getJsonString(item);
    }

    @Override
    String getSaveUrl() {
        return "items";
    }

}
