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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
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
                    LovHandler lovHandler = new LovHandler("projects", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Project>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Project>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
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
                id.setText(splitId(name.getText()) + "");
                name.setText(project.getName());
               desc.setText(project.getDesc());
                remarks.setText(project.getRemarks());
                
                client.setText(project.getClient().getName() + " (" + project.getClient().getId() + ")");
                
                
                
            }
        });
        
        
        
        TextFields.bindAutoCompletion(client, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<Client>>() {

            @Override
            public Collection<Client> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<Client> list = null;
                HttpHelper helper = new HttpHelper();
                try {
                    LovHandler lovHandler = new LovHandler("clients", "name");
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<Client>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<Client>>() {
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
                throw new UnsupportedOperationException("Not supported yet.");
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

        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(getClient().getText());
        Integer clientId = null;
        while (m.find()) {
            clientId = Integer.parseInt(m.group(1).split(":")[1]);
        }

        client.setId(clientId);

        project.setClient(client);
        
        System.out.println("getId(this.name.getText() .... " + id.getText());
        if (id.getText().equals("")) {
            System.out.println("id is  null... " + id.getText());

        } else {
            project.setId(Integer.valueOf(id.getText()));
        }

        return new JsonHelper().getJsonString(project);
    }

    @Override
    public String getSaveUrl() {
        return "projects";
    }

    public  String delete(ActionEvent event) throws IOException {
         System.out.println("getId(this.name.getText() .... "+splitId(this.name.getText()));
        String response = delete("projects",splitId(this.name.getText()));
        
        
       clear();
        return response;
       
    }
    
    void clear() {
    
       name.setText("");
       this.client.setText("");
       this.desc.setText("");       
        this.remarks.setText("");
        this.id.setText("");
     
    }
    @Override
    protected void postSave(String response) {
        JsonHelper helper = new JsonHelper();
        System.out.println("response .... " + response);
        try {
            Project project = (Project)helper.convertJsonStringToObject(response, new TypeReference<Project>() {
            });
            id.setText(project.getId().toString());
        } catch (IOException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
