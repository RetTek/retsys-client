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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.http.impl.client.HttpClients;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.main.OperationHandler;
import retsys.client.model.Client;
import retsys.client.model.Project;

/**
 * FXML Controller class
 *
 * @author Muthu
 */
public class ProjectController extends StandardController implements Initializable {

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
    
    private Map<String,Client> clients;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(client, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() {

            @Override
            public Collection<Client> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Client> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    String response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpGetObj("clients/name/" + param.getUserText()));
                    List<Client> clients = (List<Client>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Client>>() {});
                    list = clients;
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return list;
            }
        },new StringConverter<Client>() {

            @Override
            public String toString(Client object) {
                return object.getName();
            }

            @Override
            public Client fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
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

    @Override
    public String buildRequestMsg() {
        Project project = new Project();
        Client client = new Client();

        project.setName(getName().getText());
        project.setRemarks(getRemarks().getText());

        client.setId(new Integer(1));

        project.setClient(client);

        return new JsonHelper().getJsonString(project);
    }

    @Override
    public String getSaveUrl() {
        return "projects";
    }

}
