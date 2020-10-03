package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class viewAsociatieController implements Initializable {

    @FXML
    private Button btnAdauga;

    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdauga.setOnAction(e -> {
            adaugaAsociatii();
        });
    }
    @FXML
    void adaugaAsociatii() {
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("adaugAsociatii"));
            AdaugaAsociatiiController conroller = new AdaugaAsociatiiController();
            loader.setController(conroller);
            loader.load();
            Parent root;
            Scene scene = new Scene(loader.getRoot());
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
