/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.http.impl.client.HttpClients;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.dialog.Dialogs;
import retsys.client.helper.LovHandler;
import retsys.client.http.HttpHelper;
import retsys.client.json.JsonHelper;
import retsys.client.model.Model;

/**
 *
 * @author ranju
 */
public abstract class StandardController {

    public void init() {

    }

    public String save(ActionEvent event) throws IOException {
        String jsonRequest = buildRequestMsg();
        String response = null;

        HttpHelper helper = new HttpHelper();
        response = helper.executeHttpRequest(HttpClients.createDefault(), helper.getHttpPostObj(getSaveUrl(), jsonRequest));

        if ("!ERROR!".equals(response)) {
            displayMessage(true, "Save failed!");
        } else {
            displayMessage(false, "Save success!");
        }

        return response;
    }

    public void displayMessage(boolean error, String message) {
        Dialogs.create().title(error ? "Error" : "Information").message(message).showInformation();
    }

    public Integer getId(String text) {
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(text);
        Integer id = null;
        while (m.find()) {
            id = Integer.parseInt(m.group(1).split(":")[1]);
        }

        return id;
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
                    list = (List<T>)  new JsonHelper().convertJsonStringToObject(response, new TypeReference<List<T>>() {
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

    abstract String buildRequestMsg();

    abstract String getSaveUrl();
}
