/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import retsys.client.report.PrintHandler;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class INVENTORY_REPORTController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
    
        return reportmap;
    }
}
