/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ranju
 */
public class RetsysClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        loadProperties();
        Parent root = FXMLLoader.load(getClass().getResource("/retsys/client/fxml/Login.fxml"));

        //Parent root = FXMLLoader.load(getClass().getResource("/retsys/client/fxml/Dashboard.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/retsys/client/css/Login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        //stage.setFullScreen(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadProperties() {
        Properties props = new Properties();
        try {
            System.out.println("read properties file, if present");
            InputStream stream = new FileInputStream(System.getProperty("user.home")+File.separator+"retsys.properties");
            System.out.println("property file stream: " + stream);
            if (stream != null) {
                props.load(stream);
                AppContext.getInstance().setProperties(props);
                System.out.println("properties file found and loaded");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
