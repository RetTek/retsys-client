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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import retsys.client.model.POItem;
import retsys.client.model.Project;
import retsys.client.model.PurchaseOrder;
import retsys.client.model.PurchaseOrderDetail;
import retsys.client.model.Vendor;


/**
 * FXML Controller class
 *
 * @author Fahad
 */
public class DeliveryChallanReturnController extends StandardController implements Initializable {
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

    
    private ObservableList<DeliveryChallanDetail> dcDetailRecs = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dc_date.setValue(LocalDate.now());
        
        material_name.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, String>("name"));
        brand_name.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, String>("brand"));
        model_code.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, String>("model"));
        units.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, String>("model"));
        quantity.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, Integer>("quantity"));
        amount.setCellValueFactory(new PropertyValueFactory<DeliveryChallanReturnController.DCItem, Integer>("amount"));
        
        dcDetail.getColumns().setAll(material_name, brand_name, model_code, quantity,amount);
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
            }
        });
        
          AutoCompletionBinding<DeliveryChallan> bindForProject = TextFields.bindAutoCompletion(project, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<DeliveryChallan>>() {

            @Override
            public Collection<DeliveryChallan> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<DeliveryChallan> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("deliverychallans", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<DeliveryChallan>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<DeliveryChallan>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<DeliveryChallan>() {

            @Override
            public String toString(DeliveryChallan object) {
                System.out.println("here..." + object);

                String strDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(LocalDateTime.ofInstant(object.getChallanDate().toInstant(), ZoneId.systemDefault()));
                return "Project:" + object.getProject().getName() + " DC Date:" + strDate + " DC No.:" + object.getId();
            }

            @Override
            public DeliveryChallan fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });

        bindForProject.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<DeliveryChallan>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<DeliveryChallan> event) {
                DeliveryChallan dc = event.getCompletion();
                dc_no.setText(dc.getId().toString());
                dc_date.setValue(LocalDateTime.ofInstant(dc.getChallanDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
                dc_no.setText(dc.getId().toString());
                project.setText(dc.getProject().getName() + " (ID:" + dc.getProject().getId() + ")");

                ObservableList<DCItem> items = FXCollections.observableArrayList();
                Iterator detailsIt = dc.getDeliveryChallanDetail().iterator();
                while (detailsIt.hasNext()) {
                    DeliveryChallanDetail detail = (DeliveryChallanDetail) detailsIt.next();
                    Item item = detail.getItem();
                    int id = item.getId();
                    String site = item.getSite();
                    String name = item.getName();
                    String brand = item.getBrand();
                    String model = null;
                    int quantity = detail.getQuantity();
                    int amount = detail.getAmount();
                    String units = detail.getUnits();

                    items.add(new DCItem(id, name + " (ID:" + id + ")", brand, model, quantity,units,amount));
                }
                dcDetail.setItems(items);
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
        dc.setIsDelivery(false);
        
        DeliveryChallan originalChallanDetail = new DeliveryChallan();
        originalChallanDetail.setId(Integer.parseInt(dc_no.getText()));
        dc.setOriginalDeliveryChallan(originalChallanDetail);

        Iterator<DeliveryChallanReturnController.DCItem> items = dcDetail.getItems().iterator();
        List<DeliveryChallanDetail> dcDetails = new ArrayList<>();
        
        while (items.hasNext()) {
            DeliveryChallanReturnController.DCItem dcItem = items.next();
            DeliveryChallanDetail dcDetail = new DeliveryChallanDetail();
            
            Item item = new Item();
            item.setId(dcItem.id.get());
            
            dcDetail.setItem(item);
            dcDetail.setQuantity(dcItem.quantity.get());
            dcDetail.setUnits(dcItem.units.get());
            dcDetail.setAmount(dcItem.amount.get());
            
            dcDetails.add(dcDetail);
        }
        
        dc.setDeliveryChallanDetail(dcDetails);

        return new JsonHelper().getJsonString(dc);
    }

    @Override
    String getSaveUrl() {
        return "deliverychallans";
    }
    public class DCItem {

        private SimpleIntegerProperty id = new SimpleIntegerProperty();
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty brand = new SimpleStringProperty();
        private SimpleStringProperty model = new SimpleStringProperty();
        private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
        private SimpleStringProperty units = new SimpleStringProperty();
        private SimpleIntegerProperty amount = new SimpleIntegerProperty();

        public DCItem(int id, String name, String brand, String model, int quantity, String units, int amount) {
            this.id.set(id);
            this.name.set(name);
            this.brand.set(brand);
            this.model.set(model);
            this.quantity.set(quantity);
            this.units.set(units);
            this.amount.set(amount);
        }


        public StringProperty nameProperty() {
            if (name == null) {
                name = new SimpleStringProperty(this, "name");
            }

            return name;
        }

        public StringProperty brandProperty() {
            if (brand == null) {
                brand = new SimpleStringProperty(this, "brand");
            }

            return brand;
        }

        public StringProperty modelProperty() {
            if (model == null) {
                model = new SimpleStringProperty(this, "model");
            }

            return model;
        }

        public IntegerProperty quantityProperty() {
            if (quantity == null) {
                quantity = new SimpleIntegerProperty(this, "quantity");
            }

            return quantity;
        }
        
        public StringProperty unitsProperty() {
            if (units == null) {
                units = new SimpleStringProperty(this, "units");
            }

            return units;
        }
        
        public IntegerProperty amountProperty() {
            if (amount == null) {
                amount = new SimpleIntegerProperty(this, "amount");
            }

            return amount;
        }
    }
    
    public void addItem(ActionEvent event) {
        
        ObservableList<DeliveryChallanReturnController.DCItem> list = dcDetail.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }

        DeliveryChallanReturnController.DCItem item = new DeliveryChallanReturnController.DCItem((int)txt_name.getUserData(), txt_name.getText(), txt_brand.getText(), txt_model.getText(), Integer.parseInt(txt_qty.getText()), txt_units.getText(), Integer.parseInt(txt_amount.getText()));
        list.add(item);
        dcDetail.setItems(list);
    }

    public void deleteItem(ActionEvent event) {
        if (dcDetail.getSelectionModel().getSelectedItem() != null) {
            dcDetail.getItems().remove(dcDetail.getSelectionModel().getSelectedItem());
        }
    }    
    
}
