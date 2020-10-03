package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdaugaAsociatiiController implements Initializable {

    @FXML
    private Pane viewPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtCIF;

    @FXML
    private TextField txtDenumire;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
