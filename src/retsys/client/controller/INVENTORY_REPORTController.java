/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.Client;
import retsys.client.model.DCItem;
import retsys.client.model.DeliveryChallan;
import retsys.client.model.DeliveryChallanDetail;
import retsys.client.model.Project;
import retsys.client.report.PrintHandler;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class INVENTORY_REPORTController extends StandardController implements Initializable {

    @FXML
    private TextField PROJECT;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        AutoCompletionBinding<Project> txt_name = TextFields.bindAutoCompletion(name, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Project>>() {

            @Override
            public Collection<Project> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Project> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("projects", "PROJECT");
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

        //event handler for setting other Client fields with values from selected Client object
        //fires after autocompletion
        txt_name.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<Project>>() {

            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<Project> event) {
                Project project = event.getCompletion();
                //fill other item related fields
                
                PROJECT.setText(splitName(name.getText()));
               
                
                //populateAuditValues(client);
            }
        });
        // TODO
    }  
    
     @FXML
    private void printRep(ActionEvent event) {
      PrintHandler printhandler =new PrintHandler("Inventory_report", getReportDataMap());
      System.out.println(printhandler.generatePrintData());
    }
    public Map getReportDataMap()
    {
        Map reportmap =new HashMap();
        
        List<DeliveryChallan> list = null;
        try{
            LovHandler lovHandler = new LovHandler("deliverychallans", "name");
            String response = lovHandler.getSuggestions(PROJECT.getText());
            list = (List<DeliveryChallan>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<DeliveryChallan>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        
        List dcItemRow =  new ArrayList();
        reportmap.put("SiteName", PROJECT.getText());
        for (DeliveryChallan listDeliveryChallan : list){
            Iterator<DeliveryChallanDetail> items = listDeliveryChallan.getDeliveryChallanDetail().iterator();
            
            while (items.hasNext()) {
                DeliveryChallanDetail dcDetail = items.next();
                List dcItemList = new ArrayList();
                dcItemList.add(listDeliveryChallan.getId().toString());
                dcItemList.add(listDeliveryChallan.getChallanDate().toString());
                dcItemList.add(dcDetail.getName());
                dcItemList.add(dcDetail.getQuantity().toString());
                dcItemList.add(dcDetail.getAmount().toString());
                dcItemRow.add(dcItemList);
            }
            
        }
        reportmap.put("DCDETAIL", dcItemRow);
        
    
        return reportmap;
    }

    /**
     * @return the PROJECT
     */
    public TextField getPROJECT() {
        return PROJECT;
    }

    /**
     * @param PROJECT the PROJECT to set
     */
    public void setPROJECT(TextField PROJECT) {
        this.PROJECT = PROJECT;
    }
    
    public String buildRequestMsg(){
        return "";
    }

    public String getSaveUrl(){
        return "";
    }
}
