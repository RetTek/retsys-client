/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.http.impl.client.HttpClients;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.Dialogs;
import retsys.client.helper.DateUtils;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.main.AppContext;
import retsys.client.model.Audit;
import retsys.client.model.Model;

/**
 *
 * @author ranju
 */
public abstract class StandardController {

    @FXML
    protected VBox functionNode;
    @FXML
    protected TextField createdBy;
    @FXML
    protected DatePicker createdOn;
    @FXML
    protected TextField modifiedBy;
    @FXML
    protected DatePicker modifiedOn;
    @FXML
    protected TextField auditId;

    @FXML
    protected TextArea remarks;
    @FXML
    protected TextField name;

    @FXML
    public TextField id;

    protected void init(Model model) {

    }
    
    public void populateAuditValues(Model model) {
        auditId.setText(String.valueOf(model.getAudit().getId()));
        createdBy.setText(model.getAudit().getCreatedBy());
        createdOn.setValue(DateUtils.asLocalDate(model.getAudit().getCreatedOn()));

        modifiedBy.setText(model.getAudit().getModifiedBy());
        modifiedOn.setValue(DateUtils.asLocalDate(model.getAudit().getModifiedOn()));
    }

    public void setAuditValues() {
        if(id != null && id.getText().isEmpty()){
            auditId.setText("");
            createdBy.setText("");
            createdOn.setValue(null);
            modifiedBy.setText("");
            modifiedOn.setValue(null);
        }
        if (!createdBy.getText().isEmpty()) { //modification
            modifiedBy.setText(AppContext.getInstance().getUsername());
            modifiedOn.setValue(LocalDate.now());
        } else { //new record
            createdBy.setText(AppContext.getInstance().getUsername());
            createdOn.setValue(LocalDate.now());
        }

    }

    private Audit getAudit() {
        Audit audit = new Audit();
        if (!auditId.getText().isEmpty()) {
            audit.setId(Integer.parseInt(auditId.getText()));
        }
        audit.setCreatedBy(createdBy.getText());
        audit.setCreatedOn(DateUtils.asDate(createdOn.getValue()));
        audit.setModifiedBy(modifiedBy.getText());
        audit.setModifiedOn(DateUtils.asDate(modifiedOn.getValue()));

        return audit;
    }

    private String getJsonRequestWithAudit(Model model) {
        String jsonRequest = null;
        model.setAudit(getAudit());

        JsonHelper jsonHelper = new JsonHelper();
        jsonRequest = jsonHelper.getJsonString(model);

        return jsonRequest;
    }

    public String save(ActionEvent event) throws IOException {
        Model model = (Model) buildRequestMsg();
        setAuditValues();
        String jsonRequest = getJsonRequestWithAudit(model);

        String response = null;

        HttpHelper helper = new HttpHelper();
        System.out.println(getSaveUrl() + "\njson request: " + jsonRequest);
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpPostObj(getSaveUrl(), jsonRequest));

        if ("!ERROR!".equals(response)) {
            displayMessage(true, "Save failed!");
        } else {
            displayMessage(false, "Save success!");
            postSave(response);
        }

        return response;
    }

    public String update(ActionEvent event) throws IOException {
        Model model = (Model) buildRequestMsg();

        setAuditValues();

        String jsonRequest = getJsonRequestWithAudit(model);

        String response = null;

        HttpHelper helper = new HttpHelper();
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpPutObj(getSaveUrl(), jsonRequest));

        if ("!ERROR!".equals(response)) {
            displayMessage(true, "Update failed!");
        } else {
            displayMessage(false, "Update success!");
        }

        return response;
    }

    public String delete(String operation, Integer id) throws IOException {
        String response = null;

        HttpHelper helper = new HttpHelper();
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpDeleteObj(operation, id));

        if ("!ERROR!".equals(response)) {
            displayMessage(true, "Delete failed!");
        } else {
            displayMessage(false, "Delete success!");
        }

        return response;
    }

    public void displayMessage(boolean error, String message) {
        Dialogs.create().title(error ? "Error" : "Information").message(message).showInformation();
    }

    public Integer splitId(String text) {
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(text);
        Integer id = null;
        while (m.find()) {
            id = Integer.parseInt(m.group(1).split(":")[1]);
        }

        return id;
    }

    public String splitName(String text) {
        // Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(text);

        System.out.println(getSaveUrl() + "\n text: " + text);

        System.out.println(getSaveUrl() + "\n name: " + text.split("\\(")[0].trim());

        return text.split("\\(")[0].trim();
    }

    //generic method to bind auto completion to text fields!
    //set AutoCompletionBinding.AutoCompletionEvent to handle auto completion event using setOnAutoCompleted
    protected <T extends Model> AutoCompletionBinding<T> bindForAutocompletion(TextField control, String entity, String filter) {
        return TextFields.bindAutoCompletion(control, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>>() {

            @Override
            public Collection<T> call(AutoCompletionBinding.ISuggestionRequest param) {
                List<T> list = null;
                try {
                    LovHandler lovHandler = new LovHandler(entity, filter);
                    String response = lovHandler.getSuggestions(param.getUserText());
                    list = (List<T>) new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<T>>() {
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return list;
            }
        }, new StringConverter<T>() {

            @Override
            public String toString(T object) {
                System.out.println("here..." + object);
                return object.getName() + " (ID:" + object.getId() + ")";
            }

            @Override
            public T fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
    }

    protected String query(String operation) throws IOException {
        String response = null;

        HttpHelper helper = new HttpHelper();
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpGetObj(operation));

        if ("!ERROR!".equals(response)) {
            displayMessage(true, "Save failed!");
        } else {
            displayMessage(false, "Save success!");
        }

        return response;

    }
    public Integer getId(String text) {
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(text);
        Integer id = null;
        while (m.find()) {
            id = Integer.parseInt(m.group(1).split(":")[1]);
        }

        return id;
    }
    protected void postSave(String response){

    }
public void enableid () throws IOException {
id.editableProperty().setValue(Boolean.TRUE);
}
    abstract Object buildRequestMsg();

    abstract String getSaveUrl();

}
