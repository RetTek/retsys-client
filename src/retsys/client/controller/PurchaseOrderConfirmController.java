/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.awt.Event;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.DateUtils;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.model.Item;
import retsys.client.model.POItem;
import retsys.client.model.Project;
import retsys.client.model.PurchaseOrder;
import retsys.client.model.PurchaseOrderDetail;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class PurchaseOrderConfirmController extends StandardController implements Initializable {

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
    private TableColumn<POItem, Boolean> confirm;
    @FXML
    private TableColumn<POItem, LocalDate> receivedDate;
    @FXML
    private TableColumn<POItem, String> billNo;
    @FXML
    private TableColumn<POItem, String> supervisor;
    @FXML
    private TextField vendor;
    @FXML
    private TextField project;
    @FXML
    private TextField po_no;
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

    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

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
        confirm.setCellValueFactory(new PropertyValueFactory<POItem, Boolean>("confirm"));
        confirm.setCellFactory(CheckBoxTableCell.forTableColumn(confirm));
        billNo.setCellValueFactory(new PropertyValueFactory<POItem, String>("billNo"));
        billNo.setCellFactory(TextFieldTableCell.forTableColumn());
        supervisor.setCellValueFactory(new PropertyValueFactory<POItem, String>("supervisor"));
        supervisor.setCellFactory(TextFieldTableCell.forTableColumn());
        receivedDate.setCellValueFactory(new PropertyValueFactory<POItem, LocalDate>("receivedDate"));
        receivedDate.setCellFactory(new Callback<TableColumn<POItem, LocalDate>, TableCell<POItem, LocalDate>>() {

            @Override
            public TableCell<POItem, LocalDate> call(TableColumn<POItem, LocalDate> param) {
                TableCell<POItem, LocalDate> cell = new TableCell<POItem, LocalDate>() {

                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(formatter.format(item));
                        }
                    }

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        System.out.println("start edit");
                        DatePicker dateControl = null;
                        if (this.getItem() != null) {
                            dateControl = new DatePicker(this.getItem());
                        } else {
                            dateControl = new DatePicker();
                        }

                        dateControl.valueProperty().addListener(new ChangeListener<LocalDate>() {

                            @Override
                            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                                if(newValue==null){
                                    cancelEdit();
                                }else{
                                    commitEdit(newValue);
                                }
                            }
                        });
                        this.setGraphic(dateControl);
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        System.out.println("cancel edit");
                        setGraphic(null);
                        if (this.getItem() != null) {
                            setText(formatter.format(this.getItem()));
                        } else {
                            setText(null);
                        }
                    }

                    @Override
                    public void commitEdit(LocalDate newValue) {
                        super.commitEdit(newValue);
                        System.out.println("commit edit");
                        setGraphic(null);
                        setText(formatter.format(newValue));
                    }
                };

                return cell;
            }
        });

        poDetail.getColumns().setAll(loc_of_material, material_name, brand_name, model_code, quantity, confirm, receivedDate,billNo,supervisor);
        AutoCompletionBinding<PurchaseOrder> bindForTxt_name = TextFields.bindAutoCompletion(project, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<PurchaseOrder>>() {

            @Override
            public Collection<PurchaseOrder> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<PurchaseOrder> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("purchaseorders", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<PurchaseOrder>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<PurchaseOrder>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<PurchaseOrder>() {

            @Override
            public String toString(PurchaseOrder object) {
                System.out.println("here..." + object);

                String strDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(LocalDateTime.ofInstant(object.getDate().toInstant(), ZoneId.systemDefault()));
                return "Project:" + object.getProject().getName() + " PO Date:" + strDate + " PO No.:" + object.getId();
            }

            @Override
            public PurchaseOrder fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });

        bindForTxt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<PurchaseOrder>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<PurchaseOrder> event) {
                PurchaseOrder po = event.getCompletion();
                po_no.setText(po.getId().toString());
                po_date.setValue(LocalDateTime.ofInstant(po.getDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
                po_no.setText(po.getId().toString());
                delivery_address.setText(po.getDeliveryAddress());
                vendor.setText(po.getVendor().getName() + " (ID:" + po.getVendor().getId() + ")");
                project.setText(po.getProject().getName() + " (ID:" + po.getProject().getId() + ")");

                ObservableList<POItem> items = FXCollections.observableArrayList();
                Iterator detailsIt = po.getPurchaseOrderDetail().iterator();
                while (detailsIt.hasNext()) {
                    PurchaseOrderDetail detail = (PurchaseOrderDetail) detailsIt.next();
                    Item item = detail.getItem();
                    int id = item.getId();
                    String site = item.getSite();
                    String name = item.getName();
                    String brand = item.getBrand();
                    String model = null;
                    Double quantity = detail.getQuantity();
                    Boolean confirm = "Y".equals(detail.getConfirm());

                    items.add(new POItem(detail.getId(), site, name + " (ID:" + id + ")", brand, model, quantity, confirm, detail.getReceivedDate()));
                }
                poDetail.setItems(items);
            }
        });

        AutoCompletionBinding<Vendor> bindForVendor = TextFields.bindAutoCompletion(vendor, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Vendor>>() {

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
                throw new UnsupportedOperationException();
            }
        });
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
        return project;
    }

    /**
     * @param client the client to set
     */
    public void setClient(TextField client) {
        this.project = client;
    }

    /**
     * @return the Po_no
     */
    public TextField getPo_no() {
        return po_no;
    }

    /**
     * @param Po_no the Po_no to set
     */
    public void setPo_no(TextField Po_no) {
        this.po_no = Po_no;
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

        POItem item = new POItem((int) txt_location.getUserData(), txt_location.getText(), txt_name.getText(), txt_brand.getText(), txt_model.getText(), Integer.parseInt(txt_qty.getText()), false,null);
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
        PurchaseOrder po = new PurchaseOrder();
        po.setId(Integer.parseInt(po_no.getText()));
        po.setDate(Date.from(Instant.now()));

        Project projectObj = new Project();
        projectObj.setId(getId(project.getText()));
        po.setProject(projectObj);

        po.setDeliveryAddress(delivery_address.getText());

        Vendor vendorObj = new Vendor();
        vendorObj.setId(getId(vendor.getText()));
        po.setVendor(vendorObj);

        Iterator<POItem> items = poDetail.getItems().iterator();
        List<PurchaseOrderDetail> poDetails = new ArrayList<>();

        while (items.hasNext()) {
            POItem poItem = items.next();
            PurchaseOrderDetail poDetail = new PurchaseOrderDetail();

            Item item = new Item();
            item.setId(getId(poItem.getName().get()));
            poDetail.setItem(item);

            poDetail.setId(poItem.getId().get());
            poDetail.setQuantity(poItem.getQuantity().get());
            poDetail.setConfirm(poItem.getConfirm().get() ? "Y" : "N");
            poDetail.setReceivedDate(DateUtils.asDate(poItem.getReceivedDate().get()));
            poDetail.setBillNo(poItem.getBillNo().get());
            poDetail.setSupervisor(poItem.getSupervisor().get());
            
            poDetails.add(poDetail);
        }

        po.setPurchaseOrderDetail(poDetails);

        return new JsonHelper().getJsonString(po);
    }

    @Override
    String getSaveUrl() {
        return "purchaseorders/confirm";
    }
     void clear() {
    System.out.println("To be defined .... ");
     }

}
