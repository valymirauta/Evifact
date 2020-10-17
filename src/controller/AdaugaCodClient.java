package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.Main;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdaugaCodClient implements Initializable {

    @FXML
    private AnchorPane adaugaCodClient;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblFurnizor;

    @FXML
    private ComboBox<String> alegeAsociatia;

    @FXML
    private TextField txtCodClient;

    @FXML
    private TextField txtTipUtilitate;

    @FXML
    private Button btnAdauga;

    public static int IdFurnizor;
    public static int IdAsociatie;

    @FXML
    void alegeAsociatia(ActionEvent event) {

    }

    @FXML
    void adaugaCodClient(ActionEvent event) {

        String codClient = txtCodClient.getText();
        String tipUtilitate = txtTipUtilitate.getText();

        if (codClient.isEmpty() || tipUtilitate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill required fields!");
            alert.showAndWait();
        } else {
            String selected = alegeAsociatia.getValue();

            String query1 = "SELECT Id FROM asociatii WHERE denumireScurta=?";
            try {
                PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query1);
                pstmt.setString(1, selected);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    IdAsociatie = rs.getInt("Id");
                }

                String query2 = "INSERT INTO codClient (codClient,tipUtilitate, asociatiiID,furnizoriID) VALUES (?,?,?,?)";
                PreparedStatement pstmt1 = DatabaseHandler.conn.prepareStatement(query2);
                pstmt1.setString(1, codClient);
                pstmt1.setString(2, tipUtilitate);
                pstmt1.setInt(3, IdAsociatie);
                pstmt1.setInt(4, IdFurnizor);
                pstmt1.executeUpdate();

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
        adaugaCodClient.getScene().getWindow(). hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        String query = "SELECT denumireScurta FROM asociatii ";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                alegeAsociatia.getItems().add(rs.getString("denumireScurta"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void setFurnizor(int Id, String denumire) {
        lblFurnizor.setText(denumire);
        IdFurnizor = Id;
    }

}


