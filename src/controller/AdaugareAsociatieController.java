package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdaugareAsociatieController implements Initializable {

    @FXML
    private AnchorPane adaugaAsociatiePane;

    @FXML
    private TextField txtCIF;

    @FXML
    private TextField txtDenumire;

    @FXML
    private TextField txtAdresa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void inchide(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void adaugaAsociatie(ActionEvent event) {

        String CIF = txtCIF.getText();
        String denumire = txtDenumire.getText();
        String adresa = txtAdresa.getText();

        if (CIF.isEmpty() || denumire.isEmpty() || adresa.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill required fields!");
            alert.showAndWait();
        } else {
            String query = "INSERT INTO asociatii (CIF, denumire, adresa) VALUES (?,?,?)";
            try {
                PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
                pstmt.setString(1, CIF);
                pstmt.setString(2, denumire);
                pstmt.setString(3, adresa);
                pstmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Operation failed");
                alert.showAndWait();
                System.out.println(ex);
            }
        }

        adaugaAsociatiePane.getScene().getWindow().hide();


    }
}


