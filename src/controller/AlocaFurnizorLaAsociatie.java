package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Asociatii;

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

    ObservableList<String> furnizor = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String query = "SELECT * FROM furnizori";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("denumire"));
                furnizor.add(rs.getString("denumire"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listViewAsocFurn.getItems().addAll(furnizor);
        //listViewAsocFurn.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listViewAsocFurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ObservableList<String> selectedItems = listViewAsocFurn.getSelectionModel().getSelectedItems();
                int IdFurnizor;
                String query = "SELECT * FROM furnizori WHERE denumire=?";
                String query1 = "INSERT INTO asociatii_furnizori (asociatiiID,furnizoriID) VALUES(?,?)";

                try {
                    PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
                    String string = String.valueOf(selectedItems);
                    pstmt.setString(1, string);

                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        IdFurnizor = rs.getInt("Id");
                    }


                        PreparedStatement pstmt1 = DatabaseHandler.conn.prepareStatement(query1);

                        pstmt1.setInt(1, 1);
                        pstmt1.setInt(2, IdFurnizor);
                        pstmt1.executeUpdate();
                    
                    } catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
    }






