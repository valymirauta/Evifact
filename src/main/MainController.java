package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private Pane viewPane;

    @FXML
    private Button btnAsociatii;

    @FXML
    public void viewAsociatii(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        viewPane = object.getPage("viewAsociatie");
        mainPane.setCenter(viewPane);
    }

    @FXML
    void viewFurnizori(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        viewPane = object.getPage("adaugAsociatii");
        mainPane.setCenter(viewPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
