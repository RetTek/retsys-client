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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.json.JsonHelper;
import retsys.client.model.CreditNote;
import retsys.client.model.CreditNoteDetail;
import retsys.client.model.CreditNoteItem;
import retsys.client.model.Item;
import retsys.client.model.Vendor;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class CreditNoteController extends StandardController implements Initializable {

    @FXML
    private TableView<CreditNoteItem> creditNoteDetail;
    @FXML
    private TableColumn<CreditNoteItem, String> itemName;
    @FXML
    private TableColumn<CreditNoteItem, Double> itemAmount;
    @FXML
    private TableColumn<CreditNoteItem, Boolean> confirm;
    @FXML
    private TableColumn<CreditNoteItem, Double> returnQuantity;
    @FXML
    private TextField vendor;
    @FXML
    private DatePicker creationDate;
    @FXML
    private TextField creditNoteNo;
    @FXML
    private TextField totalCredit;
    @FXML
    private TextField name;
    @FXML
    private TextField brand;
    @FXML
    private TextField model;
    @FXML
    private TextField amount;
    @FXML
    private TextField quantity;

    private ObservableList<CreditNoteDetail> poDetailRecs = FXCollections.observableArrayList();
    int sno = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        creationDate.setValue(LocalDate.now());

        itemName.setCellValueFactory(new PropertyValueFactory<CreditNoteItem, String>("itemName"));
        itemAmount.setCellValueFactory(new PropertyValueFactory<CreditNoteItem, Double>("itemAmount"));
        returnQuantity.setCellValueFactory(new PropertyValueFactory<CreditNoteItem, Double>("returnQuantity"));
        confirm.setCellValueFactory(new PropertyValueFactory<CreditNoteItem, Boolean>("confirm"));
        confirm.setCellFactory(CheckBoxTableCell.forTableColumn(confirm));

        creditNoteDetail.getColumns().setAll(itemName, itemAmount, returnQuantity, confirm);

        AutoCompletionBinding<Item> bindForTxt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Item>>() {

            @Override
            public Collection<Item> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Item> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("items", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Item>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Item>>() {
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
                name.setText(item.getName() + " (ID:" + item.getId() + ")");
                brand.setText(item.getBrand());
                model.setText(null); //?? add model?
                amount.setText(item.getRate().toString());
                quantity.setText(item.getQuantity().toString());
            }
        });

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
                throw new UnsupportedOperationException();
            }
        });
    }

    public void addItem(ActionEvent event) {
        sno++;
        ObservableList<CreditNoteItem> list = creditNoteDetail.getItems();
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        Integer id = null;
        if (!creditNoteNo.getText().isEmpty()) {
            id = Integer.parseInt(creditNoteNo.getText());
        }
        CreditNoteItem item = new CreditNoteItem(name.getText(), Double.parseDouble(amount.getText()), Double.parseDouble(quantity.getText()), true);
        list.add(item);
        totalCredit.setText(Double.toString(Double.sum(Double.parseDouble(totalCredit.getText() + "0"), Double.parseDouble(amount.getText()) * Double.parseDouble(quantity.getText()))));
        creditNoteDetail.setItems(list);
    }

    public void deleteItem(ActionEvent event) {
        if (creditNoteDetail.getSelectionModel().getSelectedItem() != null) {
            CreditNoteItem item = creditNoteDetail.getSelectionModel().getSelectedItem();
            Double itemAmt = item.getItemAmount().get() * item.getReturnQuantity().get();
            Double newAmt = Double.parseDouble(totalCredit.getText() + "0") - itemAmt;
            totalCredit.setText(Double.toString(newAmt));
            creditNoteDetail.getItems().remove(item);
        }
    }

    @Override
    protected String buildRequestMsg() {
        CreditNote creditNote = new CreditNote();
        if (!creditNoteNo.getText().isEmpty()) {
            // update operation
            creditNote.setId(Integer.parseInt(creditNoteNo.getText()));
        }
        Vendor vendorObj = new Vendor();
        vendorObj.setId(splitId(vendor.getText()));
        creditNote.setVendor(vendorObj);

        Date date = Date.from(Instant.from(creationDate.getValue().atStartOfDay(ZoneId.systemDefault())));
        creditNote.setCreationDate(date);
        creditNote.setTotalAmount(Double.parseDouble(totalCredit.getText()));
        creditNote.setRemarks(remarks.getText());

        List<CreditNoteDetail> details = new ArrayList<>();
        Iterator<CreditNoteItem> items = creditNoteDetail.getItems().iterator();

        while (items.hasNext()) {
            CreditNoteItem creditNoteItem = items.next();
            Item item = new Item();
            //item.setId(getId(creditNoteItem.getItemName().get()));
            item.setId(splitId(creditNoteItem.getItemName().get()));
            if (creditNoteItem.getId().get() != 0) {
                // update operation
                details.add(new CreditNoteDetail(creditNoteItem.getId().get(), item, creditNoteItem.getReturnQuantity().get(), creditNoteItem.getItemAmount().get(), creditNoteItem.getConfirm().get()));
            } else {
                details.add(new CreditNoteDetail(item, creditNoteItem.getReturnQuantity().get(), creditNoteItem.getItemAmount().get(), creditNoteItem.getConfirm().get()));
            }
        }

        creditNote.setCreditNoteDetails(details);

        return new JsonHelper().getJsonString(creditNote);
    }

    @Override
    String getSaveUrl() {
        return "creditnotes";
    }

    @Override
    protected void postSave(String response) {
        try {
            System.out.println("response: " + response);
            CreditNote creditNote = (CreditNote) new JsonHelper().convertJsonStringToObject(response, new TypeReference<CreditNote>() {
            });
            creditNoteNo.setText(creditNote.getId().toString());

            Iterator<CreditNoteDetail> it = creditNote.getCreditNoteDetails().iterator();
            creditNoteDetail.setItems(null);
            ObservableList<CreditNoteItem> items = FXCollections.observableArrayList();
            while (it.hasNext()) {
                CreditNoteDetail creditNoteChild = it.next();

                items.add(new CreditNoteItem(creditNoteChild.getId(), creditNoteChild.getItem().getName() + " (ID:" + creditNoteChild.getItem().getId() + ")", creditNoteChild.getAmount(), creditNoteChild.getReturnQuantity(), creditNoteChild.isConfirm()));
            }

            creditNoteDetail.setItems(items);

        } catch (IOException ex) {
            Logger.getLogger(CreditNoteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    void clear() {
    System.out.println("To be defined .... ");
     }
}
