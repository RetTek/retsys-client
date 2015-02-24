/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.fxml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.json.Json;
import javax.json.JsonWriter;
import retsys.client.main.OperationHandler;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class ProjectController implements Initializable {
    @FXML
    private Tab tab_project;
    @FXML
    private TextField client;
    @FXML
    private Label lbl_project_code;
    @FXML
    private Label lbl_project_name;
    @FXML
    private TextField name;
    @FXML
    private TextField desc;
    @FXML
    private Label lbl_project_remarks;
    @FXML
    private Label lbl_project_desc;
    @FXML
    private TextArea remarks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
     * @return the name
     */
    public TextField getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(TextField name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public TextField getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(TextField desc) {
        this.desc = desc;
    }

    /**
     * @return the remarks
     */
    public TextArea getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(TextArea remarks) {
        this.remarks = remarks;
    }
  
    public void processProject(ActionEvent event){
        String json = "";
        OperationHandler opthandler = new OperationHandler();      


        
        System.out.println("Entered Here.. ");
        try{
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(pController.getName());*/
            OutputStream out = new ByteArrayOutputStream();
            try (final JsonWriter jsonWriter = Json.createWriter(out)) {
                   jsonWriter.write(Json.createObjectBuilder()
                           .add("name", this.getName().getText())
                           .add("remarks", this.getRemarks().getText())
                           .add("client", this.getClient().getText())
                           .build());
               }
            opthandler.OperationHandler(out,"Project","Create");
   } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
