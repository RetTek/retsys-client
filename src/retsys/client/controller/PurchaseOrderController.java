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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.main.PrintHandler;
import retsys.client.model.Client;
import retsys.client.model.Item;
import retsys.client.model.Model;
import retsys.client.model.PurchaseOrder;
import retsys.client.model.PurchaseOrderDetail;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class PurchaseOrderController extends StandardController implements Initializable {

    @FXML
    private TableView<POItem> poDetail;
    @FXML
    private TableColumn<POItem, String> loc_of_material;
    @FXML
    private TableColumn<POItem, String> material_name;
    @FXML
    private TableColumn<POItem, String> brand_name;
    @FXML
    private TableColumn<POItem, String> model_code;
    @FXML
    private TableColumn<POItem, Integer> quantity;
    @FXML
    private TextField vendor;
    @FXML
    private TextField client;
    @FXML
    private TextField Po_no;
    @FXML
    private Label lbl_po_no;
    @FXML
    private DatePicker po_date;
    @FXML
    private TextArea delivery_address;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_location;
    @FXML
    private TextField txt_brand;
    @FXML
    private TextField txt_model;
    @FXML
    private TextField txt_qty;

    private ObservableList<PurchaseOrderDetail> poDetailRecs = FXCollections.observableArrayList();
    int sno = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        po_date.setValue(LocalDate.now());

        loc_of_material.setCellValueFactory(new PropertyValueFactory<POItem, String>("location"));
        material_name.setCellValueFactory(new PropertyValueFactory<POItem, String>("name"));
        brand_name.setCellValueFactory(new PropertyValueFactory<POItem, String>("brand"));
        model_code.setCellValueFactory(new PropertyValueFactory<POItem, String>("model"));
        quantity.setCellValueFactory(new PropertyValueFactory<POItem, Integer>("quantity"));

        poDetail.getColumns().setAll(loc_of_material, material_name, brand_name, model_code, quantity);

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
                txt_brand.setText(item.getBrand());
                txt_location.setText(item.getSite());
                txt_location.setUserData(item.getId());
                txt_model.setText(null); // item doesn't have this field. add??
            }
        });

        AutoCompletionBinding<Client> bindForClient = TextFields.bindAutoCompletion(client, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() {

            @Override
            public Collection<Client> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Client> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("clients", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Client>)  new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Client>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
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
                throw new UnsupportedOperationException();
            }
        });
        AutoCompletionBinding<Vendor> bindForVendor = TextFields.bindAutoCompletion(vendor, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Vendor>>() {

            @Override
            public Collection<Vendor> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Vendor> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("vendors", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Vendor>)  new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Vendor>>() {
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
                throw new UnsupportedOperationException();
            }
        });
    }

    @FXML
    private void printDoc(ActionEvent event) {

        PrintHandler print = new PrintHandler();
        AnchorPane ap = new AnchorPane();

        try {
            print.Print();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @return the vendor
     */
    public TextField getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(TextField vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the client
     */
    public TextField getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(TextField client) {
        this.client = client;
    }

    /**
     * @return the Po_no
     */
    public TextField getPo_no() {
        return Po_no;
    }

    /**
     * @param Po_no the Po_no to set
     */
    public void setPo_no(TextField Po_no) {
        this.Po_no = Po_no;
    }

    /**
     * @return the lbl_po_no
     */
    public Label getLbl_po_no() {
        return lbl_po_no;
    }

    /**
     * @param lbl_po_no the lbl_po_no to set
     */
    public void setLbl_po_no(Label lbl_po_no) {
        this.lbl_po_no = lbl_po_no;
    }

    /**
     * @return the po_date
     */
    public DatePicker getPo_date() {
        return po_date;
    }

    /**
     * @param po_date the po_date to set
     */
    public void setPo_date(DatePicker po_date) {
        this.po_date = po_date;
    }

    /**
     * @return the deliver_address
     */
    public TextArea getDeliver_address() {
        return delivery_address;
    }

    /**
     * @param deliver_address the deliver_address to set
     */
    public void setDeliver_address(TextArea delivery_address) {
        this.delivery_address = delivery_address;
    }

    public void addItem(ActionEvent event) {
        sno++;
        ObservableList<POItem> list = poDetail.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }

        POItem item = new POItem((int)txt_location.getUserData(), txt_location.getText(), txt_name.getText(), txt_brand.getText(), txt_model.getText(), Integer.parseInt(txt_qty.getText()));
        list.add(item);
        poDetail.setItems(list);
    }

    public void deleteItem(ActionEvent event) {
        if (poDetail.getSelectionModel().getSelectedItem() != null) {
            poDetail.getItems().remove(poDetail.getSelectionModel().getSelectedItem());
        }
    }
    
    @Override
    String buildRequestMsg() {
        PurchaseOrder PO = new PurchaseOrder();
        PO.setDate(Date.from(Instant.now()));

        Client clientObj = new Client();
        clientObj.setId(getId(client.getText()));
        PO.setClient(clientObj);

        PO.setDeliveryAddress(delivery_address.getText());

        Vendor vendorObj = new Vendor();
        vendorObj.setId(getId(vendor.getText()));
        PO.setVendor(vendorObj);

        Iterator<POItem> items = poDetail.getItems().iterator();
        List<PurchaseOrderDetail> poDetails = new ArrayList<>();
        
        while (items.hasNext()) {
            POItem poItem = items.next();
            PurchaseOrderDetail poDetail = new PurchaseOrderDetail();
            
            Item item = new Item();
            item.setId(poItem.id.get());
            
            poDetail.setItem(item);
            poDetail.setQuantity(poItem.quantity.get());
            
            poDetails.add(poDetail);
        }
        
        PO.setPurchaseOrderDetail(poDetails);

        return new JsonHelper().getJsonString(PO);
    }

    @Override
    String getSaveUrl() {
        return "purchaseorders";
    }

    public class POItem {

        private SimpleIntegerProperty id = new SimpleIntegerProperty();
        private SimpleStringProperty location = new SimpleStringProperty();
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty brand = new SimpleStringProperty();
        private SimpleStringProperty model = new SimpleStringProperty();
        private SimpleIntegerProperty quantity = new SimpleIntegerProperty();

        public POItem(int id, String location, String name, String brand, String model, int quantity) {
            this.id.set(id);
            this.location.set(location);
            this.name.set(name);
            this.brand.set(brand);
            this.model.set(model);
            this.quantity.set(quantity);
        }

        public StringProperty locationProperty() {
            if (location == null) {
                location = new SimpleStringProperty(this, "location");
            }

            return location;
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
    }
}
