package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private DatabaseHandler handler;

    @FXML
    private BorderPane mainPane;

    @FXML
    public void viewAsociatii(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane viewPane = object.getPage("ListaAsociatii");
        mainPane.setCenter(viewPane);
    }

    @FXML
    void viewFurnizori(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane viewPane = object.getPage("ListaFurnizori");
        mainPane.setCenter(viewPane);
    }

    @FXML
    void viewFacturi(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane viewPane = object.getPage("AdaugaFacturi");
        mainPane.setCenter(viewPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = DatabaseHandler.getInstance();
    }
}
