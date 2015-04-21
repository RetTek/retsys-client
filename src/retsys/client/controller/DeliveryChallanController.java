/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.model.Client;
import retsys.client.model.DeliveryChallan;
import retsys.client.model.DeliveryChallanDetail;
import retsys.client.model.Item;
import retsys.client.model.Project;
import retsys.client.model.PurchaseOrder;
import retsys.client.model.PurchaseOrderDetail;
import retsys.client.model.Vendor;
import retsys.client.model.DCItem;


/**
 * FXML Controller class
 *
 * @author Fahad
 */
public class DeliveryChallanController extends StandardController implements Initializable {
    @FXML
    private TableView<DCItem> dcDetail;
    @FXML
    private TableColumn<DCItem, String> material_name;
    @FXML
    private TableColumn<DCItem, String> brand_name;
    @FXML
    private TableColumn<DCItem, String> model_code;
    @FXML
    private TableColumn<DCItem, Integer> quantity;
    @FXML
    private TableColumn<DCItem, String> units;
    @FXML
    private TableColumn<DCItem, Integer> amount;
    @FXML
    private TextField project;
    @FXML
    private Label lbl_projectname;
    @FXML
    private TextField deliverymode;
    @FXML
    private Label lbl_deliverymode;
    @FXML
    private TextField concernperson;
    @FXML
    private Label lbl_concernperson;
    @FXML
    private TextField dc_no;
    @FXML
    private Label lbl_po_no;
    @FXML
    private DatePicker dc_date;
    @FXML
    private Label lbl_dc_date;
    @FXML
    private Label lbl_Delivery_address;
    @FXML
    private TextArea deliver_address;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_brand;
    @FXML
    private TextField txt_model;    
    @FXML
    private TextField txt_qty;
    @FXML
    private TextField txt_units;
    @FXML
    private TextField txt_amount;
    @FXML
    private TextField txt_rate;

    
    private ObservableList<DeliveryChallanDetail> dcDetailRecs = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dc_date.setValue(LocalDate.now());
        
        material_name.setCellValueFactory(new PropertyValueFactory<DCItem, String>("name"));
        brand_name.setCellValueFactory(new PropertyValueFactory<DCItem, String>("brand"));
        model_code.setCellValueFactory(new PropertyValueFactory<DCItem, String>("model"));
        units.setCellValueFactory(new PropertyValueFactory<DCItem, String>("model"));
        quantity.setCellValueFactory(new PropertyValueFactory<DCItem, Integer>("quantity"));
        amount.setCellValueFactory(new PropertyValueFactory<DCItem, Integer>("amount"));
        
        dcDetail.getColumns().setAll(material_name, brand_name, model_code, quantity,units,amount);
        // TODO
        
        AutoCompletionBinding<Item> bindForTxt_name = TextFields.bindAutoCompletion(txt_name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Item>>() {

            @Override
            public Collection<Item> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Item> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("items", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Item>)  new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Item>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<Item>() {

            @Override
            public String toString(Item object) {
                System.out.println("here..." + object);
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Item fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        //event handler for setting other item fields with values from selected Item object
        //fires after autocompletion
        bindForTxt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Item>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Item> event) {
                Item item = event.getCompletion();
                //fill other item related fields
                txt_name.setUserData(item.getId());
                txt_brand.setText(item.getBrand());
                txt_model.setText(null); // item doesn't have this field. add??
                txt_rate.setText(String.valueOf(item.getRate()));
            }
        });
        
          AutoCompletionBinding<Project> bindForProject = TextFields.bindAutoCompletion(project, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Project>>() {

            @Override
            public Collection<Project> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Project> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("projects", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Project>)  new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Project>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<Project>() {

            @Override
            public String toString(Project object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Project fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
    }    
    
    @Override
    String buildRequestMsg() {
        DeliveryChallan dc =  new DeliveryChallan();
        dc.setChallanDate(Date.from(Instant.now()));

        Project proj = new Project();
        proj.setId(getId(project.getText()));
        dc.setProject(proj);
        dc.setIsDelivery(true);
        dc.setOriginalDeliveryChallan(null);
        dc.setDeliveryMode(deliverymode.getText());
        dc.setConcernPerson(concernperson.getText());

        Iterator<DCItem> items = dcDetail.getItems().iterator();
        List<DeliveryChallanDetail> dcDetails = new ArrayList<>();
        
        while (items.hasNext()) {
            DCItem dcItem = items.next();
            DeliveryChallanDetail dcDetail = new DeliveryChallanDetail();
            
            Item item = new Item();
            item.setId(getId(String.valueOf(dcItem.getId().get())));
            
            dcDetail.setItem(item);
            dcDetail.setQuantity(dcItem.getQuantity().get());
            dcDetail.setUnits(dcItem.getUnits().get());
            dcDetail.setAmount(dcItem.getAmount().get());
            
            dcDetails.add(dcDetail);
        }
        
        dc.setDeliveryChallanDetail(dcDetails);

        return new JsonHelper().getJsonString(dc);
    }

    @Override
    String getSaveUrl() {
        return "deliverychallans";
    }
    
    
    public void addItem(ActionEvent event) {
        
        ObservableList<DCItem> list = dcDetail.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }

        DCItem item = new DCItem((int)txt_name.getUserData(), txt_name.getText(), txt_brand.getText(), txt_model.getText(), Integer.parseInt(txt_qty.getText()), txt_units.getText(), Integer.parseInt(txt_amount.getText()));
        list.add(item);
        dcDetail.setItems(list);
    }

    public void deleteItem(ActionEvent event) {
        if (dcDetail.getSelectionModel().getSelectedItem() != null) {
            dcDetail.getItems().remove(dcDetail.getSelectionModel().getSelectedItem());
        }
    }    
    
    public void calcAmount(ActionEvent event){
        if(txt_rate.getText()!=null && txt_qty.getText()!=null){
            txt_amount.setText(String.valueOf(Integer.parseInt(txt_rate.getText()) * Integer.parseInt(txt_qty.getText())));
        }
    }
    
}
