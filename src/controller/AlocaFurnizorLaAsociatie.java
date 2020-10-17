package controller;

import database.DatabaseHandler;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AlocaFurnizorLaAsociatie implements Initializable {

    @FXML
    private AnchorPane adaugaAsociatiePane;

    @FXML
    private Label lblTitle;

    @FXML
    private ListView<String> listViewAsocFurn;


    @FXML
    private Button btnAloca;

    @FXML
    void alocaFurnizor(ActionEvent event) {
        adaugaAsociatiePane.getScene().getWindow().hide();
    }

    public static int IdFurnizor;
    public static int IdAsociatie;
    public static String Cui;

    ObservableList<String> furnizor = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String query = "SELECT * FROM furnizori";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                furnizor.add(rs.getString("denumire"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listViewAsocFurn.getItems().addAll(furnizor);

        listViewAsocFurn.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    String selectedItem = listViewAsocFurn.getSelectionModel().getSelectedItem();
                    String query1 = "SELECT Id FROM furnizori WHERE denumire=?";
                    String query2 = "INSERT INTO asociatii_furnizori (asociatiiID,furnizoriID) VALUES(?,?)";
                    try {
                        PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query1);
                        pstmt.setString(1, selectedItem);
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            IdFurnizor = rs.getInt("Id");
                        }
                        PreparedStatement pstmt1 = DatabaseHandler.conn.prepareStatement(query2);
                        pstmt1.setInt(1, IdAsociatie);
                        pstmt1.setInt(2, IdFurnizor);
                        pstmt1.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    public int getIdAsociatie(Integer Id) {
        return IdAsociatie = Id;
    }
}






