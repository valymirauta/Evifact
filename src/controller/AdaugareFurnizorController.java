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

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdaugareFurnizorController implements Initializable {
    @FXML
    private AnchorPane adaugaFurnizorPane;
    @FXML
    private TextField txtCUI;

    @FXML
    private TextField txtDenumire;

    @FXML
    private TextField txtAdresa;

    @FXML
    private Button btnAdauga;

    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void adaugaFurnizor(ActionEvent event) {

        String CUI = txtCUI.getText();
        String denumire = txtDenumire.getText();
        String adresa = txtAdresa.getText();

        if (CUI.isEmpty() || denumire.isEmpty() || adresa.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill required fields!");
            alert.showAndWait();
        } else {
            String query = "INSERT INTO furnizori (CUI, denumire, adresa) VALUES (?,?,?)";
            try {
                PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
                pstmt.setString(1, CUI);
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
        adaugaFurnizorPane.getScene().getWindow().hide();
    }

}


