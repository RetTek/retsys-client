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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.Item;
import retsys.client.model.Product;
import retsys.client.model.Vendor;
import retsys.client.model.Project;

/**
 * FXML Controller class
 *
 * @author ranju
 */
public class ItemController extends StandardController implements Initializable {

    @FXML
    private TextField brand;
    @FXML
    private TextField quantity;
    @FXML
    private TextField unit;
    @FXML
    private TextField color;
    @FXML
    private TextField rate;
    @FXML
    private TextField size;
    @FXML
    private TextField vendor;
    @FXML
    private TextField billno;
    @FXML
    private TextField supervisor;
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
    private TextField minReorder;
    @FXML
    private TextField product;
    @FXML
    private TextField discountPercentage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Begin
        //  TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() 
        AutoCompletionBinding<Item> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Item>>() {

            @Override
            public Collection<Item> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Item> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("items", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Item>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Item>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }

        }, new StringConverter<Item>() {

            @Override
            public String toString(Item object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Item fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Item>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Item> event) {
                Item item = event.getCompletion();
                //fill other item related fields
                id.setText(splitId(name.getText()) + "");
                name.setText(item.getName());
                rate.setText(item.getRate() + "");
                brand.setText(item.getBrand());
                color.setText(item.getColor());
                unit.setText(item.getUnit());
                size.setText(item.getSize());
                billno.setText(item.getBillno());
                Site.setText(item.getSite());
                remarks.setText(item.getRemarks());
                quantity.setText(item.getQuantity() + "");
                unit.setText(item.getUnit());
                transport_mode.setText(item.getTransportmode());
                transport_charges.setText(item.getTransportcharge() + "");
                supervisor.setText(item.getSupervisor());
                vendor.setText(item.getVendor().getName() + " (ID:" + item.getVendor().getId() + ")");
                godown_name.setText(item.getGodownName());
                location1.setText(item.getLocation1());
                
                minReorder.setText(item.getMinReorder());
                product.setText(item.getProduct().getName() + " (ID:"+ item.getProduct().getId() +")");
                discountPercentage.setText(item.getDiscountPercentage()+"");
                
                
            }
        });

        //Ends
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
//Project query
        
        TextFields.bindAutoCompletion(Site, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Project>>() {

            @Override
            public Collection<Project> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Project> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("projects", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Project>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Project>>() {
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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
    //}
// 
    //}

   // product query
    TextFields.bindAutoCompletion(product, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Product>>() {

            @Override
            public Collection<Product> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Product> list = null;
                try {
                    LovHandler lovHandler = new LovHandler("products", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Product>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Product>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<Product>() {

            @Override
            public String toString(Product object) {
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public Product fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }   
    @Override
    String buildRequestMsg() {
        Item item = new Item();
        Vendor vendorObj = new Vendor();
        Product productObj = new Product();

        item.setName(name.getText());
        item.setRate(new Double(rate.getText()));
        item.setBrand(brand.getText());
        item.setColor(color.getText());
        item.setUnit(unit.getText());
        item.setSize(size.getText());
        item.setBillno(billno.getText());
        item.setSite(Site.getText());
        item.setRemarks(remarks.getText());
        item.setQuantity(new Double(quantity.getText()));
        item.setTransportmode(transport_mode.getText());
        item.setTransportcharge(new Double(transport_charges.getText()));
        item.setSupervisor(supervisor.getText());
        item.setGodownName(godown_name.getText());
        item.setLocation1(location1.getText());
        item.setLocation2(location2.getText());
        item.setLocation3(location3.getText());

        vendorObj.setId(splitId(vendor.getText()));
        item.setVendor(vendorObj);
        
        productObj.setId(splitId(product.getText()));
        item.setProduct(productObj);
        item.setMinReorder(minReorder.getText());
        item.setDiscountPercentage(new Double(discountPercentage.getText()));
        
        System.out.println("getId(this.name.getText() .... " + id.getText());
        if (id.getText().equals("")) {
            System.out.println("id is  null... " + id.getText());

        } else {
            item.setId(Integer.valueOf(id.getText()));
        }

        return new JsonHelper().getJsonString(item);
    }

    @Override
    String getSaveUrl() {
        return "items";
    }
    
    public  String delete(ActionEvent event) throws IOException {
         System.out.println("getId(this.name.getText() .... "+this.id.getText());
        String response = delete("items",Integer.valueOf(this.id.getText()));     
        
        clear();
        return response;
        
       
    }
    
    void clear() {
    
    name.setText("");
        rate.setText("");
        brand.setText("");
        color.setText("");
        unit.setText("");
        size.setText("");
        billno.setText("");
        Site.setText("");
        remarks.setText("");
        quantity.setText("");
        transport_mode.setText("");
        transport_charges.setText("");
        supervisor.setText("");
        godown_name.setText("");
        location1.setText("");
        location2.setText("");
        location3.setText("");
        vendor.setText("");
        //vendorObj        
        product.setText("");
        //productObj
        minReorder.setText("");
        discountPercentage.setText("");
        id.setText("");
    }
    @Override
    protected void postSave(String response) {
        JsonHelper helper = new JsonHelper();
        System.out.println("response .... " + response);
        try {
            Item item = (Item)helper.convertJsonStringToObject(response, new TypeReference<Item>() {
            });
            id.setText(item.getId().toString());
        } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
