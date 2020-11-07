package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Asociatii;
import model.Furnizori;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdaugaFacturaManualController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private AnchorPane adaugaFacturaManual;

    @FXML
    private ComboBox<String> alegeAsociatia;

    @FXML
    private ComboBox<String> alegeFurnizor;

    @FXML
    private TextField txtCodBare;

    @FXML
    private TextField txtCodCLient;

    @FXML
    private TextField txtSerieNr;

    @FXML
    private TextField txtDataEmitere;

    @FXML
    private TextField txtDataScadenta;

    @FXML
    private TextField txtValoare;

    @FXML
    private Button btnAdauga;

    @FXML
    private Button btnInchide;


    @FXML
    void alegeAsociatia(ActionEvent event) {

    }

    @FXML
    void alegeFurnizor(ActionEvent event) {

    }

    private FXMLLoader loader;

    public static int IdCodClient;
    public static String denScurta;
    public static String seriNrFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        curatareFormular();
    }

    @FXML
    void adaugaManual(ActionEvent event) {
        String sql = "SELECT serieNrFactura FROM facturi";
        try {
            Statement stmt = DatabaseHandler.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                seriNrFactura = rs.getString("serieNrFactura");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (txtCodCLient.getText().isEmpty() || txtSerieNr.getText().isEmpty() || txtDataEmitere.getText().isEmpty()
                || txtDataScadenta.getText().isEmpty() || txtValoare.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nu ati introdus date");
            alert.setContentText("Completati toate campurile.");
            alert.showAndWait();
            curatareFormular();
        } else {
            if (txtSerieNr.getText().equals(seriNrFactura)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Factura a fost deja preluata");
                alert.setContentText("Introduceti alta factura");
                alert.showAndWait();
                curatareFormular();
            } else {

                String query = "INSERT INTO facturi (codBare, codClient, serieNrFactura,dataEmiterii,dataScadenta,valoare,achitat,codClientID) VALUES (?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
                    pstmt.setString(1, null);
                    pstmt.setString(2, txtCodCLient.getText());
                    pstmt.setString(3, txtSerieNr.getText());
                    pstmt.setString(4, txtDataEmitere.getText());
                    pstmt.setString(5, txtDataScadenta.getText());
                    pstmt.setString(6, txtValoare.getText());
                    pstmt.setInt(7, 0);
                    pstmt.setInt(8, IdCodClient);
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
                curatareFormular();
            }
        }
    }


    @FXML
    void inchide(ActionEvent event) {
        Stage stage = (Stage) btnInchide.getScene().getWindow();
        stage.close();
    }

    private void loadData() {
        String sqlAsoc = "SELECT denumireScurta FROM asociatii ";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(sqlAsoc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                alegeAsociatia.getItems().add(rs.getString("denumireScurta"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        String sqlFur = "SELECT denumire FROM furnizori ";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(sqlFur);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                alegeFurnizor.getItems().add(rs.getString("denumire"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void curatareFormular() {


        txtCodCLient.setText("");
        txtSerieNr.setText("");
        txtDataEmitere.setText("");
        txtDataScadenta.setText("");
        txtValoare.setText("");
    }

}
