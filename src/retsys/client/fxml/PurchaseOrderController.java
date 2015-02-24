/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import retsys.client.main.PrintHandler;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class PurchaseOrderController implements Initializable {
    @FXML
    private TableColumn<?, ?> loc_of_material;
    @FXML
    private TableColumn<?, ?> material_name;
    @FXML
    private TableColumn<?, ?> brand_name;
    @FXML
    private TableColumn<?, ?> model_code;
    @FXML
    private TableColumn<?, ?> quantity;
    @FXML
    private TextField vendor;
    @FXML
    private Label lbl_shop_name;
    @FXML
    private TextField client;
    @FXML
    private Label lbl_sitename;
    @FXML
    private TextField Po_no;
    @FXML
    private Label lbl_po_no;
    @FXML
    private DatePicker po_date;
    @FXML
    private Label lbl_po_date;
    @FXML
    private Label lbl_Delivery_address;
    @FXML
    private TextArea deliver_address;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void printDoc(ActionEvent event) {
        
      PrintHandler   print = new PrintHandler();
      AnchorPane ap = new AnchorPane();
      
     try{ print.Print();}
     catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    /**
     * @return the loc_of_material
     */
    public TableColumn<?, ?> getLoc_of_material() {
        return loc_of_material;
    }

    /**
     * @param loc_of_material the loc_of_material to set
     */
    public void setLoc_of_material(TableColumn<?, ?> loc_of_material) {
        this.loc_of_material = loc_of_material;
    }

    /**
     * @return the material_name
     */
    public TableColumn<?, ?> getMaterial_name() {
        return material_name;
    }

    /**
     * @param material_name the material_name to set
     */
    public void setMaterial_name(TableColumn<?, ?> material_name) {
        this.material_name = material_name;
    }

    /**
     * @return the brand_name
     */
    public TableColumn<?, ?> getBrand_name() {
        return brand_name;
    }

    /**
     * @param brand_name the brand_name to set
     */
    public void setBrand_name(TableColumn<?, ?> brand_name) {
        this.brand_name = brand_name;
    }

    /**
     * @return the model_code
     */
    public TableColumn<?, ?> getModel_code() {
        return model_code;
    }

    /**
     * @param model_code the model_code to set
     */
    public void setModel_code(TableColumn<?, ?> model_code) {
        this.model_code = model_code;
    }

    /**
     * @return the quantity
     */
    public TableColumn<?, ?> getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(TableColumn<?, ?> quantity) {
        this.quantity = quantity;
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
        return deliver_address;
    }

    /**
     * @param deliver_address the deliver_address to set
     */
    public void setDeliver_address(TextArea deliver_address) {
        this.deliver_address = deliver_address;
    }
    
}
